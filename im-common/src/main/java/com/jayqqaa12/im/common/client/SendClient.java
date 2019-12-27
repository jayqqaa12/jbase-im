package com.jayqqaa12.im.common.client;

import com.jayqqaa12.im.common.model.consts.CacheConstants;
import com.jayqqaa12.im.common.model.consts.MqConstants;
import com.jayqqaa12.im.common.model.vo.TcpRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 业务层可以通过这个client 来主动发送消息给指定的用户
 * <p>
 * 可靠性通过MQ保证
 *
 * @author: 12
 * @create: 2019-12-26 15:16
 **/
@Component
public class SendClient {

  @Autowired
  RedisTemplate redisTemplate;

  @Autowired
  KafkaTemplate kafkaTemplate;

  /**
   * 发送消息给目标用户
   * <p>
   * 通过MQ发送给 网关节点
   *
   * @return
   */
  public boolean send(String dest, int code, Object data) {
    //通过dest 查询到 当前用户在那个gateway 节点 因为是多平台可能有多个节点都在

    List<String> list = getOnlineDest(dest);

    for (String node : list) {
      //指定节点 发送 MQ消息
      kafkaTemplate.send(MqConstants.MQ_CLIENT + node, TcpRespVO.response(code, data, dest));
    }
    return !list.isEmpty();
  }


  public boolean send(TcpRespVO respVO) {
    //通过dest 查询到 当前用户在那个gateway 节点 因为是多平台可能有多个节点都在

    List<String> list = getOnlineDest(respVO.getDest());

    for (String node : list) {
      //指定节点 发送 MQ消息
      kafkaTemplate.send(MqConstants.MQ_CLIENT + node, respVO);
    }
    return !list.isEmpty();
  }

  public List<String> getOnlineDest(String dest) {

    return redisTemplate.opsForHash().values(CacheConstants.REDIS_TCP_SESSION + dest);
  }


}
