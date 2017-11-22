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
package com.lakala.transaction.core.service.message;

import com.lakala.transaction.common.enums.NettyMessageActionEnum;
import com.lakala.transaction.common.enums.TransactionStatusEnum;
import com.lakala.transaction.common.netty.bean.HeartBeat;
import com.lakala.transaction.common.netty.bean.TxTransactionGroup;
import com.lakala.transaction.common.netty.bean.TxTransactionItem;
import com.lakala.transaction.core.netty.handler.NettyClientMessageHandler;
import com.lakala.transaction.core.service.TxManagerMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
public class NettyMessageService implements TxManagerMessageService {

    private final NettyClientMessageHandler nettyClientMessageHandler;

    @Autowired
    public NettyMessageService(NettyClientMessageHandler nettyClientMessageHandler) {
        this.nettyClientMessageHandler = nettyClientMessageHandler;
    }

    /**
     * 保存事务组 在事务发起方的时候进行调用
     *
     * @param txTransactionGroup 事务组
     * @return true 成功 false 失败
     */
    @Override
    public Boolean saveTxTransactionGroup(TxTransactionGroup txTransactionGroup) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.CREATE_GROUP.getCode());
        heartBeat.setTxTransactionGroup(txTransactionGroup);
        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Boolean) object;
        }
        return false;

    }

    /**
     * 往事务组添加事务
     *
     * @param txGroupId         事务组id
     * @param txTransactionItem 子事务项
     * @return true 成功 false 失败
     */
    @Override
    public Boolean addTxTransaction(String txGroupId, TxTransactionItem txTransactionItem) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.ADD_TRANSACTION.getCode());
        TxTransactionGroup txTransactionGroup = new TxTransactionGroup();
        txTransactionGroup.setId(txGroupId);
        txTransactionGroup.setItemList(Collections.singletonList(txTransactionItem));
        heartBeat.setTxTransactionGroup(txTransactionGroup);
        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Boolean) object;
        }
        return false;
    }

    /**
     * 获取事务组状态
     *
     * @param txGroupId 事务组id
     * @return 事务组状态
     */
    @Override
    public int findTransactionGroupStatus(String txGroupId) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.GET_TRANSACTION_GROUP_STATUS.getCode());
        TxTransactionGroup txTransactionGroup = new TxTransactionGroup();
        txTransactionGroup.setId(txGroupId);

        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Integer) object;
        }
        return TransactionStatusEnum.ROLLBACK.getCode();

    }

    /**
     * 获取事务组信息
     *
     * @param txGroupId 事务组id
     * @return TxTransactionGroup
     */
    @Override
    public TxTransactionGroup findByTxGroupId(String txGroupId) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.FIND_TRANSACTION_GROUP_INFO.getCode());
        TxTransactionGroup txTransactionGroup = new TxTransactionGroup();
        txTransactionGroup.setId(txGroupId);
        heartBeat.setTxTransactionGroup(txTransactionGroup);
        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (TxTransactionGroup) object;
        }
        return null;
    }

    /**
     * 通知tm 回滚整个事务组
     *
     * @param txGroupId 事务组id
     * @return true 成功 false 失败
     */
    @Override
    public Boolean rollBackTxTransaction(String txGroupId) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.ROLLBACK.getCode());
        TxTransactionGroup txTransactionGroup = new TxTransactionGroup();
        txTransactionGroup.setStatus(TransactionStatusEnum.ROLLBACK.getCode());
        txTransactionGroup.setId(txGroupId);
        heartBeat.setTxTransactionGroup(txTransactionGroup);
        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Boolean) object;
        }
        return false;
    }

    /**
     * 通知tm自身业务已经执行完成，等待提交事务
     * tm 收到后，进行pre_commit  再进行doCommit
     *
     * @param txGroupId 事务组id
     * @return true 成功 false 失败
     */
    @Override
    public Boolean preCommitTxTransaction(String txGroupId) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.PRE_COMMIT.getCode());
        TxTransactionGroup txTransactionGroup = new TxTransactionGroup();
        txTransactionGroup.setStatus(TransactionStatusEnum.PRE_COMMIT.getCode());
        txTransactionGroup.setId(txGroupId);

        heartBeat.setTxTransactionGroup(txTransactionGroup);
        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Boolean) object;
        }
        return false;
    }

    /**
     * 完成提交自身的事务
     *
     * @param txGroupId 事务组id
     * @param taskKey   子事务的taskKey
     * @param status    状态  {@linkplain com.lakala.transaction.common.enums.TransactionStatusEnum}
     * @return true 成功 false 失败
     */
    @Override
    public Boolean completeCommitTxTransaction(String txGroupId, String taskKey, int status) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.COMPLETE_COMMIT.getCode());
        TxTransactionGroup txTransactionGroup = new TxTransactionGroup();
        // txTransactionGroup.setStatus(TransactionStatusEnum.COMMIT.getCode());
        txTransactionGroup.setId(txGroupId);

        TxTransactionItem item = new TxTransactionItem();
        item.setTaskKey(taskKey);
        item.setStatus(status);

        txTransactionGroup.setItemList(Collections.singletonList(item));

        heartBeat.setTxTransactionGroup(txTransactionGroup);
        final Object object = nettyClientMessageHandler.sendTxManagerMessage(heartBeat);
        if (Objects.nonNull(object)) {
            return (Boolean) object;
        }
        return false;
    }

    /**
     * 异步完成自身事务的提交
     *
     * @param txGroupId 事务组id
     * @param taskKey   子事务的taskKey
     * @param status    状态  {@linkplain TransactionStatusEnum}
     */
    @Override
    public void AsyncCompleteCommitTxTransaction(String txGroupId, String taskKey, int status) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setAction(NettyMessageActionEnum.COMPLETE_COMMIT.getCode());
        TxTransactionGroup txTransactionGroup = new TxTransactionGroup();
        txTransactionGroup.setId(txGroupId);

        TxTransactionItem item = new TxTransactionItem();
        item.setTaskKey(taskKey);
        item.setStatus(status);

        txTransactionGroup.setItemList(Collections.singletonList(item));

        heartBeat.setTxTransactionGroup(txTransactionGroup);
        nettyClientMessageHandler.AsyncSendTxManagerMessage(heartBeat);
    }

    /**
     * 提交参与者事务状态
     *
     * @param txGroupId         事务组id
     * @param txTransactionItem 参与者
     * @param status            状态
     * @return true 成功 false 失败
     */
    @Override
    public Boolean commitActorTxTransaction(String txGroupId, TxTransactionItem txTransactionItem, int status) {

        return null;
    }


}
