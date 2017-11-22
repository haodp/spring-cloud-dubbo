/*
 *
 * Copyright 2017-2018 549477611@qq.com(xiaoyu)
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.lakala.transaction.core.spi.repository;

import com.alibaba.druid.pool.DruidDataSource;
import com.lakala.transaction.common.enums.CompensationCacheTypeEnum;
import com.lakala.transaction.common.exception.TransactionException;
import com.lakala.transaction.common.exception.TransactionRuntimeException;
import com.lakala.transaction.common.utils.StringUtils;
import com.lakala.transaction.core.bean.TransactionInvocation;
import com.lakala.transaction.core.bean.TransactionRecover;
import com.lakala.transaction.core.config.TxConfig;
import com.lakala.transaction.core.config.TxDbConfig;
import com.lakala.transaction.core.spi.ObjectSerializer;
import com.lakala.transaction.core.spi.TransactionRecoverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTransactionRecoverRepository implements TransactionRecoverRepository {


    private Logger logger = LoggerFactory.getLogger(JdbcTransactionRecoverRepository.class);

    private DruidDataSource dataSource;


    private String tableName;

    private ObjectSerializer serializer;

    @Override
    public void setSerializer(ObjectSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public int create(TransactionRecover recover) {
        String sql = "insert into " + tableName + "(rec_id,retried_count,create_time,last_time,rec_version,group_id,task_id,invocation)" +
                " values(?,?,?,?,?,?,?,?)";
        try {
            final byte[] serialize = serializer.serialize(recover.getTransactionInvocation());
            return executeUpdate(sql, recover.getId(), recover.getRetriedCount(), recover.getCreateTime(), recover.getLastTime(),
                    recover.getVersion(), recover.getGroupId(), recover.getTaskId(), serialize);

        } catch (TransactionException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int remove(String id) {
        String sql = "delete from " + tableName + " where rec_id = ? ";
        return executeUpdate(sql, id);
    }

    /**
     * 更新数据
     *
     * @param transactionRecover 事务对象
     * @return rows 1 成功 0 失败 失败需要抛异常
     */
    @Override
    public int update(TransactionRecover transactionRecover) throws TransactionRuntimeException {

        String sql = "update " + tableName +
                " set last_time = ?,rec_version =rec_version+ 1,retried_count =retried_count+1 where rec_id = ? and rec_version=? ";
        int success = executeUpdate(sql, new Date(), transactionRecover.getId(), transactionRecover.getVersion());
        if (success <= 0) {
            throw new TransactionRuntimeException("更新异常，数据已经被更新！");
        }
        return success;
    }


    /**
     * 根据id获取对象
     *
     * @param id 主键id
     * @return TransactionRecover
     */
    @Override
    public TransactionRecover findById(String id) {
        return null;
    }

    /**
     * 获取需要提交的事务
     *
     * @return List<TransactionRecover>
     */
    @Override
    public List<TransactionRecover> listAll() {
        String selectSql = "select * from " + tableName;
        List<Map<String, Object>> list = executeQuery(selectSql);
        List<TransactionRecover> recovers = new ArrayList<>();
        for (Map<String, Object> map : list) {
            TransactionRecover recover = new TransactionRecover();

            recover.setId(StringUtils.toStrValue(map.get("REC_ID")));
            recover.setRetriedCount(StringUtils.toIntValue(map.get("RETRIED_COUNT")));
            recover.setCreateTime(StringUtils.toDateValue(map.get("CREATE_TIME")));
            recover.setLastTime(StringUtils.toDateValue( map.get("LAST_TIME")));
            recover.setTaskId(StringUtils.toStrValue(map.get("TASK_ID")));
            recover.setGroupId(StringUtils.toStrValue(map.get("GROUP_ID")));
            recover.setVersion(StringUtils.toIntValue(map.get("REC_VERSION")));
            byte[] bytes = StringUtils.toByteValue(map.get("INVOCATION")) ;
            try {
                if(bytes != null){
                    final TransactionInvocation transactionInvocation = serializer.deSerialize(bytes, TransactionInvocation.class);
                    recover.setTransactionInvocation(transactionInvocation);
                } else {
                    recover.setTransactionInvocation(null);
                }
            } catch (TransactionException e) {
                e.printStackTrace();
            }
            recovers.add(recover);
        }
        return recovers;
    }

    /**
     * 初始化操作
     *
     * @param modelName 模块名称
     * @param txConfig  配置信息
     */
    @Override
    public void init(String modelName, TxConfig txConfig) {
        dataSource = new DruidDataSource();
        final TxDbConfig txDbConfig = txConfig.getTxDbConfig();
        dataSource.setUrl(txDbConfig.getUrl());
        dataSource.setDriverClassName(txDbConfig.getDriverClassName());
        dataSource.setUsername(txDbConfig.getUsername());
        dataSource.setPassword(txDbConfig.getPassword());
        dataSource.setInitialSize(2);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(0);
        dataSource.setMaxWait(60000);
        if(txDbConfig.getDriverClassName().contains("oracle")){
            dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        } else {
            dataSource.setValidationQuery("SELECT 1");
        }
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setPoolPreparedStatements(false);
        this.tableName = "tx_transaction_" + modelName.replaceAll("-", "_");

        // 不存在的场合, 创建表
        int isExist = checkTableExist("select count(*) from " + tableName);
        if(isExist == 1){
            executeUpdate(buildCreateTableSql(txDbConfig.getDriverClassName()));
        }
    }

    private String buildCreateTableSql(String driverClassName) {
        String createTableSql;
        String dbType = "mysql";
        if (driverClassName.contains("mysql")) {
            dbType = "mysql";
        } else if (driverClassName.contains("sqlserver")) {
            dbType = "sqlserver";
        } else if (driverClassName.contains("oracle")) {
            dbType = "oracle";
        }
        switch (dbType) {
            case "mysql": {
                createTableSql = "CREATE TABLE `" + tableName + "` (\n" +
                        "  `rec_id` varchar(64) NOT NULL,\n" +
                        "  `retried_count` int(3) NOT NULL,\n" +
                        "  `create_time` datetime NOT NULL,\n" +
                        "  `last_time` datetime NOT NULL,\n" +
                        "  `rec_version` int(6) NOT NULL,\n" +
                        "  `group_id` varchar(64) NOT NULL,\n" +
                        "  `task_id` varchar(64) NOT NULL,\n" +
                        "  `invocation` longblob NOT NULL,\n" +
                        "  PRIMARY KEY (`rec_id`)\n" +
                        ")";
                break;
            }
            case "oracle": {
                createTableSql = "CREATE TABLE " + tableName + " (\n" +
                        "  rec_id varchar(64) NOT NULL,\n" +
                        "  retried_count int NOT NULL,\n" +
                        "  create_time TIMESTAMP NOT NULL,\n" +
                        "  last_time TIMESTAMP NOT NULL,\n" +
                        "  rec_version int NOT NULL,\n" +
                        "  group_id varchar2(64) NOT NULL,\n" +
                        "  task_id varchar2(64) NOT NULL,\n" +
                        "  invocation BLOB NOT NULL,\n" +
                        "  PRIMARY KEY (rec_id)\n" +
                        ")";
                break;
            }
            case "sqlserver": {
                createTableSql = "CREATE TABLE `" + tableName + "` (\n" +
                        "  `rec_id` varchar(64) NOT NULL,\n" +
                        "  `retried_count` int(3) NOT NULL,\n" +
                        "  `create_time` datetime NOT NULL,\n" +
                        "  `last_time` datetime NOT NULL,\n" +
                        "  `rec_version` int(6) NOT NULL,\n" +
                        "  `group_id` nchar(64) NOT NULL,\n" +
                        "  `task_id` nchar(64) NOT NULL,\n" +
                        "  `invocation` varbinary NOT NULL,\n" +
                        "  PRIMARY KEY (`rec_id`)\n" +
                        ")";
                break;
            }
            default: {
                throw new RuntimeException("dbType类型不支持,目前仅支持mysql oracle sqlserver.");
            }
        }
        return createTableSql;


    }


    /**
     * 设置scheme
     *
     * @return scheme 命名
     */
    @Override
    public String getScheme() {
        return CompensationCacheTypeEnum.DB.getCompensationCacheType();
    }

    private int executeUpdate(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    Object value = params[i];
                    if (value instanceof java.sql.Date) {
                        ps.setDate(i + 1, (java.sql.Date)value);
                    } else if (value instanceof java.sql.Timestamp) {
                        ps.setTimestamp(i + 1, (java.sql.Timestamp)value);
                    } else if (value instanceof java.util.Date) {
                        Date date = (Date)value;
                        ps.setTimestamp(i + 1, new Timestamp(date.getTime()));
                    } else {
                        ps.setObject(i + 1, value);
                    }
                }
            }

            logger.info("executeUpdate sql->" + sql);
            logger.info(StringUtils.getStr(params));

            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("executeUpdate->" + e.getMessage() + " " + sql);
            logger.info(StringUtils.getStr(params));
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private List<Map<String, Object>> executeQuery(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    Object value = params[i];
                    if (value instanceof java.sql.Date) {
                        ps.setDate(i + 1, (java.sql.Date)value);
                    } else if (value instanceof java.sql.Timestamp) {
                        ps.setTimestamp(i + 1, (java.sql.Timestamp)value);
                    } else if (value instanceof java.util.Date) {
                        Date date = (Date)value;
                        ps.setTimestamp(i + 1, new Timestamp(date.getTime()));
                    } else {
                        ps.setObject(i + 1, value);
                    }
                }
            }
            logger.info("executeQuery sql->" + sql);
            logger.info(StringUtils.getStr(params));
            rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            list = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            logger.error("executeQuery->" + e.getMessage());
            logger.error("executeQuery sql->" + sql);
            logger.error(StringUtils.getStr(params));
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private int checkTableExist(String sql) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            logger.warn("checkTableExist->" + e.getMessage());
            return 1;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
