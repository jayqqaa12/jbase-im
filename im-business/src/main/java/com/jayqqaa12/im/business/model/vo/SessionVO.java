package com.jayqqaa12.im.business.model.vo;

import com.jayqqaa12.im.business.model.entity.ImSession;
import lombok.Data;

/**
 * @author: 12
 * @create: 2020-01-10 18:06
 **/
@Data
public class SessionVO extends ImSession {
  private  Integer msgCount;

}
