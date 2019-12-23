package com.jayqqaa12.im.gateway.protool.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TestDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        while (true) {


            if (in.readableBytes() < 4) {
                break;
            }


//            int len = in.readInt();
//
//            System.out.println(len);
//
//            toLittleEndian(len);

            byte[] test = new byte[in.readableBytes()];
            in.readBytes(test);

            log.info(" debug read len {} bytes {}",   (test));

            log.info(" debug read len {} bytes {}", new String (test));


            // 可以根据不同类型model type 读取不同len

//            in.markReaderIndex();
//
//            int len = in.readInt();
//
//            if (in.readableBytes() < len) {
//                break;
//            }
//
//            byte[] bytes = new byte[len];
//            in.readBytes(bytes);
//
//            log.info(" debug read {}", new String(bytes));
//
//            out.add(ctx.alloc().buffer().writeBytes(bytes));

        }

    }


    public static int toLittleEndian(int a) {
        return (((a & 0xFF) << 24) | (((a >> 8) & 0xFF) << 16) | (((a >> 16) & 0xFF) << 8) | ((a >> 24) & 0xFF));
    }


}
