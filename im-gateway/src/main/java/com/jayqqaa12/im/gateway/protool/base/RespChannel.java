package com.jayqqaa12.im.gateway.protool.base;

import com.alibaba.fastjson.JSON;
import com.jayqqaa12.im.gateway.protool.model.vo.TcpRespVO;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;


@Slf4j(topic = "SEND_MSG")
public class RespChannel implements Comparable<RespChannel> {


    private Channel channel;

    public RespChannel(Channel channel) {
        this.channel = channel;
    }


    public void close() {

        if (channel.isActive()) channel.close();
    }

    public void resp(TcpRespVO response) {

        channel.writeAndFlush(JSON.toJSONString(response));

        log.info("发送消息 resp= {} ", JSON.toJSONString(response));

    }


    @Override
    public int compareTo(RespChannel o) {
        return this.channel.id().compareTo(o.channel.id());
    }

    @Override
    public int hashCode() {
        return channel.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Channel) {
            return channel.equals(obj);
        } else if (obj instanceof RespChannel) {

            return channel.equals(((RespChannel) obj).channel);
        } else {
            return super.equals(obj);
        }

    }

    @Override
    public String toString() {
        return channel.toString();
    }
}
