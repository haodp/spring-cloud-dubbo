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
package com.lakala.gate.service.impl;

import com.lakala.transaction.core.annotation.TxTransaction;
import com.lakala.gate.api.entity.Order;
import com.lakala.gate.api.service.OrderService;
import com.lakala.gate.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

//    @Autowired
//    public OrderServiceImpl(OrderMapper orderMapper) {
//        this.orderMapper = orderMapper;
//    }


    @Override
    @TxTransaction
    public void save(Order order) {
        orderMapper.save(order);
    }

    @Override
    @TxTransaction
    public void fail(Order order) throws RuntimeException {
        orderMapper.save(null);
    }

    @Override
    @TxTransaction
    public void timeOut(Order order) {
        //正常保存
        orderMapper.save(order);
        try {
            //模拟超时
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
