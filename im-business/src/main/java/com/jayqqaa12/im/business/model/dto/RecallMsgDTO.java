package com.jayqqaa12.im.business.model.dto;

import com.jayqqaa12.im.business.model.consts.SessionType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: 12
 * @create: 2019-12-27 17:11
 **/
@Data
public class RecallMsgDTO {

  @NotNull
  private Long msgId;

  @NotNull
  private SessionType sessionType;





}
