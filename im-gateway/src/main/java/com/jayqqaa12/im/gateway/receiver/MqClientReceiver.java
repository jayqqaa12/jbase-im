package com.jayqqaa12.im.gateway.receiver;

import com.jayqqaa12.im.common.model.vo.TcpRespVO;
import com.jayqqaa12.im.gateway.support.SendHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 接受其他业务节点发过来的消息
 *
 * 推送给目标用户
 *
 * @author: 12
 * @create: 2019-09-11 15:38
 **/
@Component
@Slf4j
public class MqClientReceiver {

    @Autowired
    SendHelper sendHelper;

    @KafkaListener(topics = "${topic_client}")
    public void receiver(TcpRespVO response) {

        log.info("MqClientReceiver {}", response);

        sendHelper.sendAndRetryForLocal(response);

    }

}
