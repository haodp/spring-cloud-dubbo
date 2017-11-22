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
package com.lakala.transaction.core.netty;

import com.lakala.transaction.common.netty.bean.HeartBeat;
import com.lakala.transaction.core.config.TxConfig;

public interface NettyClientService {

    /**
     * 启动netty客户端
     */
    void start(TxConfig txConfig);

    /**
     * 停止服务
     */
    void stop();


    void doConnect();

    /**
     * 重启
     */
    void restart();


    /**
     * 检查状态
     *
     * @return TRUE 正常
     */
    boolean checkState();
}
