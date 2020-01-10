package com.jayqqaa12.im.business.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author: 12
 * @create: 2020-01-10 15:13
 **/
@Data
public class MsgPageDTO {

  @NotNull
  private  Long lastMsgId;

  @Max(value = 100,message = "Max size =100")
  @Min(value = 10,message = "Min Size=10")
  private Long size=10L;


}
