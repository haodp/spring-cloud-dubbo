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
package com.lakala.transaction.core.service.handler;

import com.lakala.transaction.common.enums.NettyResultEnum;
import com.lakala.transaction.common.enums.TransactionRoleEnum;
import com.lakala.transaction.common.enums.TransactionStatusEnum;
import com.lakala.transaction.common.exception.TransactionRuntimeException;
import com.lakala.transaction.common.holder.IdWorkerUtils;
import com.lakala.transaction.common.holder.LogUtil;
import com.lakala.transaction.common.netty.bean.TxTransactionGroup;
import com.lakala.transaction.common.netty.bean.TxTransactionItem;
import com.lakala.transaction.core.bean.TxTransactionInfo;
import com.lakala.transaction.core.compensation.command.TxCompensationCommand;
import com.lakala.transaction.core.concurrent.task.BlockTask;
import com.lakala.transaction.core.concurrent.task.BlockTaskHelper;
import com.lakala.transaction.core.concurrent.threadlocal.TxTransactionLocal;
import com.lakala.transaction.core.concurrent.threadpool.TransactionThreadPool;
import com.lakala.transaction.core.service.TxManagerMessageService;
import com.lakala.transaction.core.service.TxTransactionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;

@Component
public class StartTxTransactionHandler implements TxTransactionHandler {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StartTxTransactionHandler.class);

    private final TransactionThreadPool transactionThreadPool;

    private final TxManagerMessageService txManagerMessageService;

    private final TxCompensationCommand txCompensationCommand;

    private final PlatformTransactionManager platformTransactionManager;


    @Autowired(required = false)
    public StartTxTransactionHandler(TransactionThreadPool transactionThreadPool, TxManagerMessageService txManagerMessageService, TxCompensationCommand txCompensationCommand, PlatformTransactionManager platformTransactionManager) {
        this.transactionThreadPool = transactionThreadPool;
        this.txManagerMessageService = txManagerMessageService;
        this.txCompensationCommand = txCompensationCommand;
        this.platformTransactionManager = platformTransactionManager;
    }


    @Override
    public Object handler(ProceedingJoinPoint point, TxTransactionInfo info) throws Throwable {
        LogUtil.info(LOGGER, "tx-transaction start,  事务发起类：{}",
                () -> point.getTarget().getClass());

        final String groupId = IdWorkerUtils.getInstance().createGroupId();

        //设置事务组ID
        TxTransactionLocal.getInstance().setTxGroupId(groupId);

        final String waitKey = IdWorkerUtils.getInstance().createTaskKey();

        //创建事务组信息
        final Boolean success = txManagerMessageService.saveTxTransactionGroup(newTxTransactionGroup(groupId, waitKey));
        if (success) {

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus transactionStatus = platformTransactionManager.getTransaction(def);
            try {
                //发起调用
                final Object res = point.proceed();

                //保存本地补偿数据
                String compensateId = txCompensationCommand.saveTxCompensation(info.getInvocation(), groupId, waitKey);

                final Boolean commit = txManagerMessageService.preCommitTxTransaction(groupId);
                if (commit) {
                    //我觉得到这一步了，应该是切面走完，然后需要提交了，此时应该都是进行提交的

                    //提交事务
                    platformTransactionManager.commit(transactionStatus);


                    //删除补偿信息
                    txCompensationCommand.removeTxCompensation(compensateId);

                    //通知tm完成事务
                    CompletableFuture.runAsync(() ->
                            txManagerMessageService
                                    .AsyncCompleteCommitTxTransaction(groupId, waitKey,
                                            TransactionStatusEnum.COMMIT.getCode()));


                } else {
                    System.out.println("预提交失败!");
                    LOGGER.error("预提交失败!");
                    platformTransactionManager.rollback(transactionStatus);
                }
                LogUtil.info(LOGGER, "tx-transaction end,  事务发起类：{}",
                        () -> point.getTarget().getClass());
                return res;

            } catch (final Throwable throwable) {
                //如果有异常 当前项目事务进行回滚 ，同时通知tm 整个事务失败
                platformTransactionManager.rollback(transactionStatus);

                //通知tm整个事务组失败，需要回滚，（回滚那些正常提交的模块，他们正在等待通知。。。。）
                txManagerMessageService.rollBackTxTransaction(groupId);

                throwable.printStackTrace();
                throw throwable;
            } finally {
                TxTransactionLocal.getInstance().removeTxGroupId();
            }
        } else {
            throw new TransactionRuntimeException("TxManager 连接异常！");
        }


    }


    private TxTransactionGroup newTxTransactionGroup(String groupId, String taskKey) {
        //创建事务组信息
        TxTransactionGroup txTransactionGroup = new TxTransactionGroup();
        txTransactionGroup.setId(groupId);

        List<TxTransactionItem> items = new ArrayList<>(2);

        //tmManager 用redis hash 结构来存储 整个事务组的状态做为hash结构的第一条数据

        TxTransactionItem groupItem = new TxTransactionItem();
        groupItem.setStatus(TransactionStatusEnum.BEGIN.getCode());//整个事务组状态为开始
        groupItem.setTransId(groupId); //设置事务id为组的id  即为 hashKey
        groupItem.setTaskKey(groupId);
        groupItem.setRole(TransactionRoleEnum.START.getCode());

        items.add(groupItem);

        TxTransactionItem item = new TxTransactionItem();
        item.setTaskKey(taskKey);
        item.setTransId(IdWorkerUtils.getInstance().createUUID());
        item.setRole(TransactionRoleEnum.START.getCode());
        item.setStatus(TransactionStatusEnum.BEGIN.getCode());
        item.setTxGroupId(groupId);
        items.add(item);

        txTransactionGroup.setItemList(items);
        return txTransactionGroup;
    }


}
