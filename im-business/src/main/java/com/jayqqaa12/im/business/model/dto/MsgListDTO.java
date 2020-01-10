package com.jayqqaa12.im.business.model.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author: 12
 * @create: 2020-01-10 16:13
 **/
@Data
@ToString(callSuper = true)
public class MsgListDTO extends MsgPageDTO {

  @NotNull
  private Long sessionId;
}
