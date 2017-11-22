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

import com.google.common.collect.Lists;
import com.lakala.transaction.common.enums.CompensationCacheTypeEnum;
import com.lakala.transaction.common.exception.TransactionException;
import com.lakala.transaction.common.exception.TransactionIOException;
import com.lakala.transaction.common.exception.TransactionRuntimeException;
import com.lakala.transaction.common.holder.LogUtil;
import com.lakala.transaction.core.bean.TransactionRecover;
import com.lakala.transaction.core.config.TxConfig;
import com.lakala.transaction.core.config.TxZookeeperConfig;
import com.lakala.transaction.core.spi.ObjectSerializer;
import com.lakala.transaction.core.spi.TransactionRecoverRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;


public class ZookeeperTransactionRecoverRepository implements TransactionRecoverRepository {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperTransactionRecoverRepository.class);

    private ObjectSerializer objectSerializer;

    private String rootPath = "/tx";

    private String modelName;

    private static volatile ZooKeeper zooKeeper;

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);


    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * 创建本地事务对象
     *
     * @param transactionRecover 事务对象
     * @return rows
     */

    @Override
    public int create(TransactionRecover transactionRecover) {
        try {
            zooKeeper.create(getRootPath(transactionRecover.getId()),
                    objectSerializer.serialize(transactionRecover),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return 1;
        } catch (Exception e) {
            throw new TransactionIOException(e);
        }
    }

    /**
     * 删除对象
     *
     * @param id 事务对象id
     * @return rows
     */
    @Override
    public int remove(String id) {
        try {
            final TransactionRecover byId = findById(id);
            zooKeeper.delete(getRootPath(id), byId.getVersion() - 1);
            return 1;
        } catch (Exception e) {
            throw new TransactionIOException(e);
        }
    }

    /**
     * 更新数据
     *
     * @param transactionRecover 事务对象
     * @return rows 1 成功 0 失败 失败需要抛异常
     */
    @Override
    public int update(TransactionRecover transactionRecover) throws TransactionRuntimeException {
        try {
            transactionRecover.setLastTime(new Date());
            transactionRecover.setVersion(transactionRecover.getVersion() + 1);
            transactionRecover.setRetriedCount(transactionRecover.getRetriedCount() + 1);
            zooKeeper.setData(getRootPath(transactionRecover.getId()),
                    objectSerializer.serialize(transactionRecover), transactionRecover.getVersion() - 2);
            return 1;
        } catch (Exception e) {
            throw new TransactionIOException(e);
        }
    }

    /**
     * 根据id获取对象
     *
     * @param id 主键id
     * @return TransactionRecover
     */
    @Override
    public TransactionRecover findById(String id) {
        try {
            Stat stat = new Stat();
            byte[] content = zooKeeper.getData(getRootPath(id), false, stat);
            return objectSerializer.deSerialize(content, TransactionRecover.class);
        } catch (Exception e) {
            throw new TransactionIOException(e);
        }
    }

    /**
     * 获取需要提交的事务
     *
     * @return List<TransactionRecover>
     */
    @Override
    public List<TransactionRecover> listAll() {
        List<TransactionRecover> transactionRecovers = Lists.newArrayList();

        List<String> zNodePaths;
        try {
            zNodePaths = zooKeeper.getChildren(rootPath, false);
        } catch (Exception e) {
            throw new TransactionIOException(e);
        }
        if (CollectionUtils.isNotEmpty(zNodePaths)) {
            transactionRecovers = zNodePaths.stream()
                    .filter(StringUtils::isNoneBlank)
                    .map(zNodePath -> {
                        try {
                            byte[] content = zooKeeper.getData(getRootPath(zNodePath), false, new Stat());
                            return objectSerializer.deSerialize(content, TransactionRecover.class);
                        } catch (KeeperException | InterruptedException | TransactionException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).collect(Collectors.toList());
        }

        return transactionRecovers;
    }

    /**
     * 初始化操作
     *
     * @param modelName 模块名称
     * @param txConfig  配置信息
     */
    @Override
    public void init(String modelName, TxConfig txConfig) {
        this.modelName = modelName;
        final String zkRootPath = txConfig.getTxZookeeperConfig().getRootPath();
        if (StringUtils.isNoneBlank(zkRootPath)) {
            String path = String.join("_", zkRootPath, modelName);
            setRootPath(path);
        } else {
            setRootPath(String.join("_", this.rootPath, modelName));
        }

        try {
            connect(txConfig.getTxZookeeperConfig());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "zookeeper连接异常请检查配置信息是否正确:{}", e::getMessage);
            throw new TransactionRuntimeException(e.getMessage());
        }


    }

    private void connect(TxZookeeperConfig config) {
        try {
            zooKeeper = new ZooKeeper(config.getHost(), config.getSessionTimeOut(), watchedEvent -> {
                if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    // 放开闸门, wait在connect方法上的线程将被唤醒
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            Stat stat = zooKeeper.exists(rootPath, false);
            if (stat == null) {
                zooKeeper.create(rootPath, rootPath.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            throw new TransactionIOException(e);
        }


    }

    /**
     * 设置scheme
     *
     * @return scheme 命名
     */
    @Override
    public String getScheme() {
        return CompensationCacheTypeEnum.ZOOKEEPER.getCompensationCacheType();
    }

    /**
     * 设置序列化信息
     *
     * @param objectSerializer 序列化实现
     */
    @Override
    public void setSerializer(ObjectSerializer objectSerializer) {
        this.objectSerializer = objectSerializer;
    }

    private String getParentPath() {
        return String.join("/", rootPath, modelName);
    }

    private String getRootPath(String id) {
        return String.join("/", rootPath, id);
    }


}
