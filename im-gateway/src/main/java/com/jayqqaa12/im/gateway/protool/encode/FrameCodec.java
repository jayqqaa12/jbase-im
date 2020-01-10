package com.jayqqaa12.im.gateway.protool.encode;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * Created by 12 on 2017/12/4.
 */
public class FrameCodec extends MessageToMessageCodec<TextWebSocketFrame, Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {

        out.add(new TextWebSocketFrame(JSON.toJSONString(msg)));
    }





    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) {
        out.add(msg.text());
    }


}
