package com.jayqqaa12.im.gateway.receiver;

import com.jayqqaa12.im.gateway.protool.model.vo.TcpRespVO;
import com.jayqqaa12.im.gateway.support.SendHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 默认用mq实现
 * <p>
 * 接受其他节点过来的消息进行发送
 *
 * @author: 12
 * @create: 2019-09-11 15:38
 **/
@Component
@Slf4j
public class MqForwardReceiver {

  @Autowired
  SendHelper sendHelper;

  @KafkaListener(topics = "${topic_forward}")
  public void receiver(TcpRespVO response) {

    log.info("MqForwardReceiver {}", response);

    sendHelper.sendAndRetryForLocal(response);


  }

}
