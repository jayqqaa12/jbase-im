package com.jayqqaa12.im.gateway.protool.base;

import com.alibaba.fastjson.JSON;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.common.model.vo.TcpReqVO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


@ChannelHandler.Sharable
@Slf4j(topic = "RECEIVE_MSG")
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger LOG = LoggerFactory.getLogger(ServerHandler.class);


    private static final String TCP_CONTENT = "tcp_content";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        ctx.channel().attr(AttributeKey.valueOf("heart")).set(System.currentTimeMillis());

        TcpContext context = getTcpContent(ctx.channel());
        try {
            log.info("receive  user[{}]  channel[{}]  msg: {} ", context.getUserOrDevice(), ctx.channel(), msg);
            if (StringUtils.isNotEmpty(msg)) {
                TcpReqVO request = JSON.parseObject(msg, TcpReqVO.class);
                RouterChain.run(context,request);
            }

        } catch (Throwable e) {
            log.error("receive the msg[" + msg + "] throw a exception ", e);
            context.error( "数据解析异常 " + e.getMessage());
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOG.info("client connect to server {},{}", ctx.channel().remoteAddress(), ctx.channel());
        TcpContext context = new TcpContext();
        context.setConnectTime(System.currentTimeMillis());
        context.setRespChannel(new RespChannel(ctx.channel()));

        ctx.executor().schedule(() -> {
            //10s 还没有登录就disconnect
            if (!context.isLogin()) ctx.disconnect();
        }, 10, TimeUnit.SECONDS);

        ctx.channel().attr(AttributeKey.valueOf(TCP_CONTENT)).setIfAbsent(context);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        discardChannel(ctx.channel());
        LOG.info("client disconnect to server {},{}", ctx.channel().remoteAddress(), ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 对全局异常进行处理 返回错误信息
        LOG.error("throw exception {}", cause);
    }


    private void discardChannel(Channel ch) throws Exception {

        TcpContext context = getTcpContent(ch);

        RouterChain.exec(new TcpReqVO().setCode(Req.UNREGISTER), context);
        LOG.info("user {} discardChannel {} ", context.getUserOrDevice(), ch);

        if (ch.isActive()) ch.close();
    }


    private TcpContext getTcpContent(Channel channel) {

        return (TcpContext) channel.attr(AttributeKey.valueOf(TCP_CONTENT)).get();
    }
}
