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
import com.lakala.transaction.common.exception.TransactionRuntimeException;
import com.lakala.transaction.core.bean.TransactionRecover;
import com.lakala.transaction.core.config.TxConfig;
import com.lakala.transaction.core.config.TxFileConfig;
import com.lakala.transaction.core.spi.ObjectSerializer;
import com.lakala.transaction.core.spi.TransactionRecoverRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public class FileTransactionRecoverRepository implements TransactionRecoverRepository {


    private String filePath;

    private volatile static boolean initialized;


    private ObjectSerializer serializer;

    @Override
    public void setSerializer(ObjectSerializer serializer) {
        this.serializer = serializer;
    }

    /**
     * 创建本地事务对象
     *
     * @param transactionRecover 事务对象
     * @return rows
     */
    @Override
    public int create(TransactionRecover transactionRecover) {
        writeFile(transactionRecover);
        return 1;
    }

    /**
     * 删除对象
     *
     * @param id 事务对象id
     * @return rows
     */
    @Override
    public int remove(String id) {
        String fullFileName = getFullFileName(id);
        File file = new File(fullFileName);
        if (file.exists()) {
            file.delete();
        }
        return 1;
    }

    /**
     * 更新数据
     *
     * @param transactionRecover 事务对象
     * @return rows 1 成功 0 失败 失败需要抛异常
     */
    @Override
    public int update(TransactionRecover transactionRecover) throws TransactionRuntimeException {
        transactionRecover.setLastTime(new Date());
        transactionRecover.setVersion(transactionRecover.getVersion() + 1);
        transactionRecover.setRetriedCount(transactionRecover.getRetriedCount() + 1);
        try {
            writeFile(transactionRecover);
        } catch (Exception e) {
            throw new TransactionRuntimeException("更新数据异常！");
        }
        return 1;
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
        List<TransactionRecover> transactionRecoverList = Lists.newArrayList();
        File path = new File(filePath);
        File[] files = path.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                TransactionRecover transaction = readTransaction(file);
                assert transaction != null;
                if (transaction.getVersion() == 1) {
                    transactionRecoverList.add(transaction);
                    transaction.setVersion(transaction.getVersion() + 1);
                    writeFile(transaction);
                }
            }
        }
        return transactionRecoverList;
    }


    @Override
    public void init(String modelName, TxConfig txConfig) {
        filePath = buildFilePath(modelName, txConfig.getTxFileConfig());
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.mkdirs();
        }
    }

    private String buildFilePath(String modelName, TxFileConfig txFileConfig) {

        String fileName = String.join("_", "TX", txFileConfig.getPrefix(), modelName.replaceAll("-", "_"));

        return String.join("/", txFileConfig.getPath(), fileName);


    }

    /**
     * 设置scheme
     *
     * @return scheme 命名
     */
    @Override
    public String getScheme() {
        return CompensationCacheTypeEnum.FILE.getCompensationCacheType();
    }

    private void writeFile(TransactionRecover transaction) {
        makeDirIfNecessory();

        String file = getFullFileName(transaction.getId());

        FileChannel channel = null;
        RandomAccessFile raf;
        try {
            byte[] content = serialize(transaction);
            raf = new RandomAccessFile(file, "rw");
            channel = raf.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(content.length);
            buffer.put(content);
            buffer.flip();

            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }

            channel.force(true);
        } catch (Exception e) {
            throw new TransactionRuntimeException(e);
        } finally {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                    throw new TransactionRuntimeException(e);
                }
            }
        }
    }

    private String getFullFileName(String id) {
        return String.format("%s/%s", filePath, id);
    }

    private TransactionRecover readTransaction(File file) {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);

            byte[] content = new byte[(int) file.length()];

            fis.read(content);

            return deserialize(content);
        } catch (Exception e) {
            throw new TransactionRuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // throw new TransactionRuntimeException(e);
                }
            }
        }
    }

    private void makeDirIfNecessory() {
        if (!initialized) {
            synchronized (FileTransactionRecoverRepository.class) {
                if (!initialized) {
                    File rootPathFile = new File(filePath);
                    if (!rootPathFile.exists()) {

                        boolean result = rootPathFile.mkdir();

                        if (!result) {
                            throw new TransactionRuntimeException("cannot create root path, the path to create is:" + filePath);
                        }

                        initialized = true;
                    } else if (!rootPathFile.isDirectory()) {
                        throw new TransactionRuntimeException("rootPath is not directory");
                    }
                }
            }
        }
    }

    private byte[] serialize(TransactionRecover transaction) throws Exception {
        return serializer.serialize(transaction);

    }

    private TransactionRecover deserialize(byte[] value) throws Exception {
        return serializer.deSerialize(value, TransactionRecover.class);
    }
}
