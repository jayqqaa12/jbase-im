package com.jayqqaa12.im.common.model.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: 12
 * @create: 2019-12-26 13:43
 **/
@Data
@Accessors(chain = true)
public class RpcDTO {

  //device 和uid 二选一

  private Long userId;
  private String device;

  private String uuid;   // 请求的唯一标示 请保证重发的消息uuid相同
  private Integer code; //请求码

  private Long timestamp;
  private String version;//指令的版本号 为以后做兼容

  private JSONObject data;
}
