package com.jayqqaa12.im.gateway.support;

import com.jayqqaa12.im.common.model.consts.MqConstants;
import com.jayqqaa12.im.gateway.protool.model.vo.TcpRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: 12
 * @create: 2019-09-17 16:41
 **/
@Component
public class ForwardHelper {

  @Autowired
  RegHelper regHelper;

    @Autowired
    KafkaTemplate kafkaTemplate;
//
  /**
   * 连接不在当前节点 转发给目标节点
   */
  public void forward(String dest, TcpRespVO resp) {

    for (String nodeId : regHelper.getOnlineDest(dest)) {
      String queue = MqConstants.MQ_FORWARD + nodeId;
        kafkaTemplate.send(queue, resp);
    }


  }

}
