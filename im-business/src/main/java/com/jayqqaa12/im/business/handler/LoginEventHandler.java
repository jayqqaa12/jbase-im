package com.jayqqaa12.im.business.handler;

import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.common.model.dto.RpcDTO;

/**
 * 登录后触发的事件
 * @author: 12
 * @create: 2019-12-27 18:04
 **/
@Handler(req = Req.BUSINESS_EVENT_LOGIN)
public class LoginEventHandler implements IHandler {

  @Override
  public Object handle(RpcDTO req, Object data) {

    //todo 返回离线消息




    return null;
  }
}
