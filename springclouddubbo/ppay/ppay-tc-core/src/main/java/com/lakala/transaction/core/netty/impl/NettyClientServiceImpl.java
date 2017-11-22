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
package com.lakala.transaction.core.netty.impl;

import com.google.common.base.StandardSystemProperty;
import com.lakala.transaction.common.entity.TxManagerServer;
import com.lakala.transaction.common.enums.SerializeProtocolEnum;
import com.lakala.transaction.common.holder.LogUtil;
import com.lakala.transaction.core.config.TxConfig;
import com.lakala.transaction.core.netty.NettyClientService;
import com.lakala.transaction.core.netty.handler.NettyClientHandlerInitializer;
import com.lakala.transaction.core.netty.handler.NettyClientMessageHandler;
import com.lakala.transaction.core.service.impl.TxManagerLocator;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class NettyClientServiceImpl implements NettyClientService {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientServiceImpl.class);

    private TxConfig txConfig;

    private EventLoopGroup workerGroup;

    private DefaultEventExecutorGroup servletExecutor;

    private String host = "127.0.0.1";

    private Integer port = 8888;

    private Channel channel;

    private Bootstrap bootstrap;

    private final NettyClientHandlerInitializer nettyClientHandlerInitializer;


    @Autowired
    public NettyClientServiceImpl(NettyClientHandlerInitializer nettyClientHandlerInitializer) {

        this.nettyClientHandlerInitializer = nettyClientHandlerInitializer;
    }


    /**
     * 启动netty客户端
     */
    @Override
    public void start(TxConfig txConfig) {
        this.txConfig = txConfig;
        SerializeProtocolEnum serializeProtocol =
                SerializeProtocolEnum.acquireSerializeProtocol(txConfig.getNettySerializer());
        nettyClientHandlerInitializer.setSerializeProtocolEnum(serializeProtocol);
        servletExecutor = new DefaultEventExecutorGroup(txConfig.getNettyThreadMax());
        nettyClientHandlerInitializer.setServletExecutor(servletExecutor);
        nettyClientHandlerInitializer.setTxConfig(txConfig);
        TxManagerLocator.getInstance().setTxConfig(txConfig);
        TxManagerLocator.getInstance().schedulePeriodicRefresh();
        try {
            bootstrap = new Bootstrap();
            groups(bootstrap, txConfig.getNettyThreadMax());
            doConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void groups(Bootstrap bootstrap, int workThreads) {
        if (Objects.equals(StandardSystemProperty.OS_NAME.value(), "Linux")) {
            workerGroup = new EpollEventLoopGroup(workThreads);
            bootstrap.group(workerGroup);
            bootstrap.channel(EpollSocketChannel.class);
            bootstrap.option(EpollChannelOption.TCP_CORK, true)
                    .option(EpollChannelOption.SO_KEEPALIVE, true)
                    .option(EpollChannelOption.CONNECT_TIMEOUT_MILLIS, 5)
                    .option(EpollChannelOption.SO_BACKLOG, 1024)
                    .option(EpollChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(nettyClientHandlerInitializer);
        } else {
            workerGroup = new NioEventLoopGroup(workThreads);
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(nettyClientHandlerInitializer);
        }
    }


    public void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        final TxManagerServer txManagerServer = TxManagerLocator.getInstance().locator();
        if (Objects.nonNull(txManagerServer) &&
                StringUtils.isNoneBlank(txManagerServer.getHost())
                && Objects.nonNull(txManagerServer.getPort())) {
            host = txManagerServer.getHost();
            port = txManagerServer.getPort();
        }

        ChannelFuture future = bootstrap.connect(host, port);
        LogUtil.info(LOGGER, "连接txManager-socket服务-> host:port:{}", () -> host + ":" + port);

        future.addListener((ChannelFutureListener) futureListener -> {
            if (futureListener.isSuccess()) {
                channel = futureListener.channel();
                LogUtil.info(LOGGER, "Connect to server successfully!-> host:port:{}", () -> host + ":" + port);
            } else {
                LogUtil.info(LOGGER, "Failed to connect to server, try connect after 5s-> host:port:{}", () -> host + ":" + port);
                futureListener.channel().eventLoop().schedule(this::doConnect, 5, TimeUnit.SECONDS);
            }
        });

    }

    /**
     * 停止服务
     */
    @Override
    public void stop() {
        if (Objects.nonNull(servletExecutor)) {
            workerGroup.shutdownGracefully();
        }
        if (Objects.nonNull(servletExecutor)) {
            servletExecutor.shutdownGracefully();
        }

    }

    /**
     * 重启
     */
    @Override
    public void restart() {
        stop();
        start(txConfig);
    }


    /**
     * 检查状态
     *
     * @return TRUE 正常
     */
    @Override
    public boolean checkState() {
        if (!NettyClientMessageHandler.net_state) {
            LogUtil.error(LOGGER, () -> "socket服务尚未建立连接成功,将在此等待2秒.");
            try {
                Thread.sleep(1000 * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!NettyClientMessageHandler.net_state) {
                LogUtil.error(LOGGER, () -> "TxManager还未连接成功,请检查TxManager服务后再试");
                return false;
            }
        }

        return true;
    }
}
