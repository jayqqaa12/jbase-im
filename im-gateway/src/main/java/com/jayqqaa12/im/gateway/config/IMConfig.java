package com.jayqqaa12.im.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.jayqqaa12.im.common.model.consts.MqConstants;
import com.jayqqaa12.im.common.util.NodeKit;
import com.jayqqaa12.im.gateway.protool.base.RouterChain;
import com.jayqqaa12.im.gateway.protool.tcp.SocketServer;
import com.jayqqaa12.im.gateway.protool.ws.WebSocketServer;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.SSLException;

@Configuration
@Slf4j
public class IMConfig {

  @Value("${tcp.socket.port}")
  Integer port;
  @Value("${tcp.ws.port}")
  Integer wsPort;
//    int ringBufferSize = 1024 * 32;//必须是2的N次方;

  @Autowired
  ApplicationContext context;

  @Autowired
  AdminClient adminClient;


  @PostConstruct
  public void init() {

    //打开内存检测
    ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);

    //设置push超时时间 发现时间太长导致消息响应太久 导致
//    System.setProperty("gexin_http_so_timeout", "5000");
//    System.setProperty("gexin_http_connecton_timeout", "5000");

    //topic 不能直接设置变量 通过系统变量设置就可以解决
    System.setProperty("topic_forward", MqConstants.MQ_FORWARD + NodeKit.getNodeId());
    System.setProperty("topic_client", MqConstants.MQ_CLIENT + NodeKit.getNodeId());


    ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    JSON.DEFAULT_GENERATE_FEATURE = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.WriteEnumUsingName, false);


    RouterChain.init(context);

  }

  @Bean
  public AdminClient admin(KafkaProperties properties) {
    return AdminClient.create(properties.buildAdminProperties());
  }

  @PreDestroy
  public void close() {
    log.info("PreDestroy clear  ");

    //关闭的时候删除topic
    adminClient.deleteTopics(Lists.newArrayList(System.getProperty("topic_forward")
      , System.getProperty("topic_client")));
  }


  @Bean("socket")
  public SocketServer socketServer() throws Exception {

    SocketServer socketServer = new SocketServer(port);
    socketServer.startServer();
    return socketServer;
  }

  @Bean("ws")
  public WebSocketServer wsServer() throws SSLException {

    WebSocketServer webSocketServer = new WebSocketServer(wsPort);
    webSocketServer.startServer();

    return webSocketServer;
  }

}
