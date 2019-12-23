package com.jayqqaa12.im.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jayqqaa12.im.gateway.protool.handler.RouterChain;
import com.jayqqaa12.im.gateway.protool.tcp.SocketServer;
import com.jayqqaa12.im.gateway.protool.ws.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
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


    @PostConstruct
    public void init() {

        //设置push超时时间 发现时间太长导致消息响应太久 导致
        System.setProperty("gexin_http_so_timeout", "5000");
        System.setProperty("gexin_http_connecton_timeout", "5000");


        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JSON.DEFAULT_GENERATE_FEATURE = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.WriteEnumUsingName, false);


        RouterChain.init("com.jayqqaa12.im.gateway", context);

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
