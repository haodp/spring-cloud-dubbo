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
package com.lakala.transaction.common.netty.serizlize;

import com.lakala.transaction.common.enums.NettyMessageActionEnum;
import com.lakala.transaction.common.enums.TransactionStatusEnum;
import com.lakala.transaction.common.holder.IdWorkerUtils;
import com.lakala.transaction.common.netty.bean.HeartBeat;
import com.lakala.transaction.common.netty.bean.TxTransactionGroup;
import com.lakala.transaction.common.netty.bean.TxTransactionItem;
import com.lakala.transaction.common.netty.serizlize.kryo.KryoPoolFactory;
import com.lakala.transaction.common.netty.serizlize.kryo.KryoSerialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SerializeTest {

    public static void main(String[] args) throws IOException {
        final long start = System.currentTimeMillis();
        for (int i = 0; i <1000000 ; i++) {
            KryoSerialize kryoSerialization = new KryoSerialize(KryoPoolFactory.getKryoPoolInstance());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String groupId = IdWorkerUtils.getInstance().createGroupId();
            //创建事务组信息
            TxTransactionGroup txTransactionGroup = new TxTransactionGroup();
            txTransactionGroup.setId(groupId);
            List<TxTransactionItem> items = new ArrayList<>(2);
            //tmManager 用redis hash 结构来存储 整个事务组的状态做为hash结构的第一条数据
            TxTransactionItem groupItem = new TxTransactionItem();
            groupItem.setStatus(TransactionStatusEnum.BEGIN.getCode());//整个事务组状态为开始
            groupItem.setTransId(groupId); //设置事务id为组的id  即为 hashKey
            groupItem.setTaskKey(groupId);
            items.add(groupItem);
            TxTransactionItem item = new TxTransactionItem();
            item.setTaskKey(IdWorkerUtils.getInstance().createTaskKey());
            item.setTransId(IdWorkerUtils.getInstance().createUUID());
            item.setStatus(TransactionStatusEnum.BEGIN.getCode());
            items.add(item);
            txTransactionGroup.setItemList(items);


            HeartBeat heartBeat = new HeartBeat();
            heartBeat.setAction(NettyMessageActionEnum.HEART.getCode());
            heartBeat.setTxTransactionGroup(txTransactionGroup);

            kryoSerialization.serialize(byteArrayOutputStream, heartBeat);


            byte[] body = byteArrayOutputStream.toByteArray();


            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

            final HeartBeat heartBeat1 = (HeartBeat)
                    kryoSerialization.deserialize(byteArrayInputStream);

        }
        final long end = System.currentTimeMillis();

        System.out.println((end-start)/1000);




    }
}
