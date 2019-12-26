package com.jayqqaa12.im.gateway.support;

import com.jayqqaa12.im.gateway.protool.model.TcpConstants;
import com.jayqqaa12.im.gateway.protool.model.vo.TcpRespVO;
import org.springframework.stereotype.Component;

/**
 * @author: 12
 * @create: 2019-09-17 16:41
 **/
@Component
public class ForwardHelper {


//    @Autowired
//    KafkaTemplate kafkaTemplate;

    /**
     * 连接不在当前节点 转发给目标节点
     */
    public void forward(String dest, TcpRespVO resp) {


        String queue = TcpConstants.MQ_FORWARD+ dest;

//        kafkaTemplate.sendMsg(queue, resp);

    }

}
