package com.jayqqaa12.im.business.model.dto;

import com.jayqqaa12.im.business.model.consts.MsgType;
import com.jayqqaa12.im.business.model.consts.SessionType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: 12
 * @create: 2019-12-27 14:49
 **/
@Data
public class SendMsgDTO {

  /**
   * 内容
   */
  @NotNull
  @NotEmpty
  private String content;

  /**
   * 消息类型 0 文字 1.表情2图片3,语音4视频 5 json
   */
  @NotNull
  private MsgType type;

  /**
   * 接受方
   */
  @NotNull
  private Long recvUid;


  /**
   * 会话类型
   */
  private SessionType sessionType;




}
