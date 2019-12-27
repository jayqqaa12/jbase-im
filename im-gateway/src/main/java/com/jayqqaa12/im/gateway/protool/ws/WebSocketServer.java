/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.jayqqaa12.im.gateway.protool.ws;

import com.alibaba.fastjson.JSON;
import com.jayqqaa12.im.gateway.protool.model.vo.TcpRespVO;
import com.jayqqaa12.im.gateway.protool.encode.FrameCodec;
import com.jayqqaa12.im.gateway.protool.base.ServerHandler;
import com.jayqqaa12.jbase.tcp.netty.NettyHeartHandler;
import com.jayqqaa12.jbase.tcp.netty.NettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import javax.net.ssl.SSLException;
import java.util.concurrent.TimeUnit;

/**
 * Created by 12 on 2017/3/15
 * <p>
 * websocket 支持列表
 * <li>Safari 5+ (draft-ietf-hybi-thewebsocketprotocol-00)
 * <li>Chrome 6-13 (draft-ietf-hybi-thewebsocketprotocol-00)
 * <li>Chrome 14+ (draft-ietf-hybi-thewebsocketprotocol-10)
 * <li>Chrome 16+ (RFC 6455 aka draft-ietf-hybi-thewebsocketprotocol-17)
 * <li>Firefox 7+ (draft-ietf-hybi-thewebsocketprotocol-10)
 * <li>Firefox 11+ (RFC 6455 aka draft-ietf-hybi-thewebsocketprotocol-17)
 * </ul>
 */
public class WebSocketServer extends NettyServer {

    private EventExecutorGroup eventExecutors = new DefaultEventExecutorGroup(10);

    private String path = "/";

    public WebSocketServer(int port) throws SSLException {
        super(port);
    }

    ServerHandler serverHandler = new ServerHandler();

//    ClassPathResource certChainFile = new ClassPathResource("server.crt");
//    ClassPathResource keyFile = new ClassPathResource("pkcs8_server.key");
//    SslContext sslCtx = SslContextBuilder.forServer(certChainFile.getStream(),
//            keyFile.getStream()).clientAuth(ClientAuth.NONE).build();

    @Override
    protected void addChannelHandler(ChannelPipeline pipeline) {
//        SSLEngine engine = sslCtx.newEngine(ByteBufAllocator.DEFAULT);
//        engine.setUseClientMode(false);
//        engine.setWantClientAuth(false);
//        pipeline.addLast(new SslHandler(engine));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(path, null, true));
        pipeline.addLast(new FrameCodec());
        pipeline.addLast(new IdleStateHandler(1, 0, 0, TimeUnit.MINUTES));
        pipeline.addLast(new NettyHeartHandler(() -> JSON.toJSONString(TcpRespVO.heart()), 60 * 3));

        pipeline.addLast(eventExecutors, serverHandler);
    }


    @Override
    protected void config(ServerBootstrap bootstrap) {
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_BACKLOG, 1024 * 10)
        ;

    }

    public void setPath(String path) {
        this.path = path;
    }


}
