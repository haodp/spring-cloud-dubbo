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
package com.lakala.transaction.tx.manager.netty.handler;

import com.lakala.transaction.common.enums.SerializeProtocolEnum;
import com.lakala.transaction.common.netty.NettyPipelineInit;
import com.lakala.transaction.common.netty.serizlize.hessian.HessianCodecService;
import com.lakala.transaction.common.netty.serizlize.hessian.HessianDecoder;
import com.lakala.transaction.common.netty.serizlize.hessian.HessianEncoder;
import com.lakala.transaction.common.netty.serizlize.kryo.KryoCodecService;
import com.lakala.transaction.common.netty.serizlize.kryo.KryoDecoder;
import com.lakala.transaction.common.netty.serizlize.kryo.KryoEncoder;
import com.lakala.transaction.common.netty.serizlize.kryo.KryoPoolFactory;
import com.lakala.transaction.common.netty.serizlize.protostuff.ProtostuffCodecService;
import com.lakala.transaction.common.netty.serizlize.protostuff.ProtostuffDecoder;
import com.lakala.transaction.common.netty.serizlize.protostuff.ProtostuffEncoder;
import com.lakala.transaction.tx.manager.config.NettyConfig;
import com.lakala.transaction.tx.manager.service.TxManagerService;
import com.netflix.discovery.converters.Auto;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyConfig nettyConfig;


    private final NettyServerMessageHandler nettyServerMessageHandler;

    private SerializeProtocolEnum serializeProtocolEnum;


    private DefaultEventExecutorGroup servletExecutor;

    public void setServletExecutor(DefaultEventExecutorGroup servletExecutor) {
        this.servletExecutor = servletExecutor;
    }

    @Autowired
    public NettyServerHandlerInitializer(NettyConfig nettyConfig, NettyServerMessageHandler nettyServerMessageHandler) {
        this.nettyConfig = nettyConfig;
        this.nettyServerMessageHandler = nettyServerMessageHandler;
    }

    public void setSerializeProtocolEnum(SerializeProtocolEnum serializeProtocolEnum) {
        this.serializeProtocolEnum = serializeProtocolEnum;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        NettyPipelineInit.serializePipeline(serializeProtocolEnum,pipeline);
        pipeline.addLast("timeout",
                new IdleStateHandler(nettyConfig.getHeartTime(), nettyConfig.getHeartTime(), nettyConfig.getHeartTime(), TimeUnit.SECONDS));
        pipeline.addLast(nettyServerMessageHandler);
    }
}
