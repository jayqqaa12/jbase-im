package com.jayqqaa12.im.business.handler;

import com.jayqqaa12.im.business.model.Handler;
import com.jayqqaa12.im.business.model.IHandler;
import com.jayqqaa12.im.common.model.consts.Req;

/**
 * @author: 12
 * @create: 2019-12-27 14:08
 **/
@Handler(req = Req.SEND_MSG)
public class MsgHandler implements IHandler {


  @Override
  public Object handle(Object data) {



    return null;
  }
}
