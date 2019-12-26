package com.jayqqaa12.im.gateway.protool.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcpReqVO {

    private String uuid;   // 请求的唯一标示 请保证重发的消息uuid相同
    private Integer code; //请求码
    private Long timestamp;

    private String version;//指令的版本号 为以后做兼容

    private Object data;

    public static TcpReqVO req(int code, Object data) {

        return new TcpReqVO().setCode(code).setTimestamp(System.currentTimeMillis())
                .setUuid(UUID.randomUUID().toString())
                .setVersion("1.6.2").setData( (data));

    }



    public static TcpReqVO req(String  uuid, int code, Object data) {


        return new TcpReqVO().setCode(code).setTimestamp(System.currentTimeMillis())
                .setUuid(uuid )
                .setVersion("1.0.0").setData( (data));

    }
}
