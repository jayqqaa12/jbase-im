package com.jayqqaa12.im.gateway.protool.tcp;

import com.alibaba.fastjson.JSON;
import com.jayqqaa12.im.common.model.vo.TcpRespVO;
import com.jayqqaa12.im.gateway.protool.base.ServerHandler;
import com.jayqqaa12.im.gateway.protool.encode.JSONEncoder;
import com.jayqqaa12.jbase.tcp.netty.NettyHeartHandler;
import com.jayqqaa12.jbase.tcp.netty.NettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 *  可优化为 epoll
 * Created by 12 on 2017/3/14.
 */
public class SocketServer extends NettyServer {

  private EventExecutorGroup eventExecutors = new DefaultEventExecutorGroup(10);

  private ServerHandler serverHandler = new ServerHandler();

  public SocketServer(int port) throws Exception {
    super(port);
  }

//    ClassPathResource certChainFile = new ClassPathResource("server.crt");
//    ClassPathResource keyFile = new ClassPathResource("pkcs8_server.key");
//    SslContext sslCtx = SslContextBuilder.forServer(certChainFile.getStream(),
//            keyFile.getStream()).clientAuth(ClientAuth.NONE).build();


  protected void addChannelHandler(ChannelPipeline pipeline) {

//        pipeline.addLast(sslCtx.newHandler(ByteBufAllocator.DEFAULT));

    pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4));
    pipeline.addLast(new JSONEncoder());
    pipeline.addLast(new StringDecoder(Charset.forName("UTF-8")));
    pipeline.addLast(new IdleStateHandler(1, 0, 0, TimeUnit.MINUTES));

//    pipeline.addLast(new LoggingHandler());// 添加log
    pipeline.addLast(new NettyHeartHandler(() -> JSON.toJSONString(TcpRespVO.heart()), 3 * 60));

    // 延迟flush 处理器
    pipeline.addLast(new FlushConsolidationHandler(5, true));

    pipeline.addLast(eventExecutors, serverHandler);


  }


  @Override
  protected void config(ServerBootstrap bootstrap) {

    bootstrap.childOption(ChannelOption.TCP_NODELAY, true)
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 10)
      .option(ChannelOption.SO_BACKLOG, 1024 * 10)
    ;

  }
}
