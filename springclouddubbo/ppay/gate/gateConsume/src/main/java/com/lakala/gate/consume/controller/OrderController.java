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
package com.lakala.gate.consume.controller;

import com.lakala.gate.consume.service.Test1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consume/order")
public class OrderController {

    @Autowired
    private Test1Service test1Service;

    @ResponseBody
    @GetMapping("/save")
    public String save() {
        return test1Service.save();
    }


    @ResponseBody
    @GetMapping("/orderFail")
    public String orderFail() {
        try {
            test1Service.testOrderFail();
        } catch (Exception e) {
            return "orderFail rollback";
        }
        return "orderFail";
    }

    @ResponseBody
    @GetMapping("/orderTimeOut")
    public String orderTimeOut() {

        try {
            test1Service.testOrderTimeOut();
        } catch (Exception e) {
            e.printStackTrace();
            return "orderTimeOut  rollback";
        }
        return "orderTimeOut";
    }


    @ResponseBody
    @GetMapping("/stockFail")
    public String stockFail() {
        try {
            test1Service.testStockFail();
        } catch (Exception e) {
            e.printStackTrace();
            return "stockFail  rollback";
        }
        return "stockFail";

    }

    @ResponseBody
    @GetMapping("/stockTimeOut")
    public String stockTimeOut() {

        try {
            test1Service.testStockTimeOut();
        } catch (Exception e) {
            e.printStackTrace();
            return "stockTimeOut  rollback";
        }
        return "stockTimeOut";

    }


}
