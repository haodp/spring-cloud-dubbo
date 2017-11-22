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
package com.lakala.gate.consume.service.impl;

import com.lakala.transaction.common.holder.IdWorkerUtils;
import com.lakala.transaction.core.annotation.TxTransaction;
import com.lakala.gate.consume.entity.Test1;
import com.lakala.gate.consume.mapper.Test1Mapper;
import com.lakala.gate.consume.service.Test1Service;
import com.lakala.gate.api.entity.Order;
import com.lakala.gate.api.service.OrderService;
import com.lakala.ppaygate.api.entity.Stock;
import com.lakala.ppaygate.api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class Test1ServiceImpl implements Test1Service {

    @Autowired
    private  Test1Mapper test1Mapper;

    @Autowired
    private  OrderService orderService;

    @Autowired
    private  StockService stockService;


//    public Test1ServiceImpl(Test1Mapper test1Mapper, OrderService orderService, StockService stockService) {
//        this.test1Mapper = test1Mapper;
//        this.orderService = orderService;
//        this.stockService = stockService;
//    }


    /**
     * 正常保存 test1表插入数据，order表插入数据 stock表插入数据
     *
     * @return
     */
    @Override
    @TxTransaction
    public String save() {
        String name = "hello_demo1";
        Test1 test = new Test1();
        double db = Math.random();

        int value = (int)db + 1;
        test.setId(value);
        test.setName(name);
        test1Mapper.save(test);

        Order order = new Order();
        order.setId(value);
        order.setCreateTime(new Date());
        order.setNumber(IdWorkerUtils.getInstance().createUUID());
        order.setStatus(0);
        order.setType(0);
        orderService.save(order);

        Stock stock = new Stock();
        stock.setId(value);
        stock.setName(IdWorkerUtils.getInstance().buildPartNumber());
        stock.setNumber(100);
        stock.setCreateTime(new Date());
        stockService.save(stock);


        return "success";
    }

    @Override
    public String testFail() {
        String name = "FIAL";
        Test1 test = new Test1();
        test.setName(name);
        test1Mapper.save(test);

        // int i = 100 / 0;
        return "fial";

    }

    /**
     * 强一致性测试
     * 测试 订单保存异常的情况
     * 此时t_test 表不会新增数据 order表不会新增数据 stock则不执行
     *
     * @return "order_fail"
     */
    @Override
    @TxTransaction
    public String testOrderFail() {
        String name = "hello_demo1";
        Test1 test = new Test1();
        test.setName(name);
        test1Mapper.save(test);

        Order order = new Order();
        orderService.fail(order);

        Stock stock = new Stock();
        stock.setName(IdWorkerUtils.getInstance().buildPartNumber());
        stock.setNumber(100);
        stock.setCreateTime(new Date());
        stockService.save(stock);
        return "order_fail";
    }

    /**
     * 强一致性测试
     * 测试 订单保存超时的情况
     * 此时t_test 表不会新增数据,order表不会新增数据 stock则不执行
     *
     * @return "order_timeOut"
     */
    @Override
    @TxTransaction
    public String testOrderTimeOut() {
        String name = "hello_demo1";
        Test1 test = new Test1();
        test.setName(name);
        test1Mapper.save(test);

        Order order = new Order();
        order.setCreateTime(new Date());
        order.setNumber(IdWorkerUtils.getInstance().createUUID());
        order.setStatus(0);
        order.setType(0);
        orderService.timeOut(order);

        Stock stock = new Stock();
        stock.setName(IdWorkerUtils.getInstance().buildPartNumber());
        stock.setNumber(100);
        stock.setCreateTime(new Date());
        stockService.save(stock);

        return "order_timeOut";
    }

    /**
     * 强一致性测试
     * 测试 stock保存异常的情况
     * 此时t_test 表不会新增数据 order表不会新增数据 stock表不会新增数据
     *
     * @return "stock_fail"
     */
    @Override
    @TxTransaction
    public String testStockFail() {
        String name = "hello_demo1";
        Test1 test = new Test1();
        test.setName(name);
        test1Mapper.save(test);

        Order order = new Order();
        order.setCreateTime(new Date());
        order.setNumber(IdWorkerUtils.getInstance().createUUID());
        order.setStatus(0);
        order.setType(0);
        orderService.save(order);

        Stock stock = new Stock();
        stockService.fail(stock);
        return "stock_fail";
    }

    /**
     * 强一致性测试
     * 测试 stock保存超时的情况
     * 此时t_test 表不会新增数据,order表不会新增数据 stock表不会新增数据
     *
     * @return "stock_timeOut"
     */
    @Override
    @TxTransaction
    public String testStockTimeOut() {
        String name = "hello_demo1";
        Test1 test = new Test1();
        test.setName(name);
        test1Mapper.save(test);

        Order order = new Order();
        order.setCreateTime(new Date());
        order.setNumber(IdWorkerUtils.getInstance().createUUID());
        order.setStatus(0);
        order.setType(0);
        orderService.save(order);

        Stock stock = new Stock();
        stock.setName(IdWorkerUtils.getInstance().buildPartNumber());
        stock.setNumber(100);
        stock.setCreateTime(new Date());
        stockService.timeOut(stock);

        return "stock_timeOut";
    }
}
