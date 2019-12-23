package com.jayqqaa12.im.gateway.protool.encode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * Created by 12 on 2017/12/4.
 */
public class FrameCodec extends MessageToMessageCodec<TextWebSocketFrame, String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {

//        ByteBuf bytebuf = Unpooled.buffer();
//        bytebuf.writeBytes(GzipUtils.gzip(URLEncoder.encode(msg, "utf8").replaceAll("\\+", "%20").getBytes())
//        );
//        out.add(new BinaryWebSocketFrame(bytebuf));

        out.add(new TextWebSocketFrame(msg));
    }





    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) {
        out.add(msg.text());
    }


}
