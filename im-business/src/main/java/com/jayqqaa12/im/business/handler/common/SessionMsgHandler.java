package com.jayqqaa12.im.business.handler.common;

import com.jayqqaa12.im.business.model.dto.MsgPageDTO;
import com.jayqqaa12.im.business.service.ISessionService;
import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.model.ReqContent;
import com.jayqqaa12.im.common.model.consts.Req;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 *
 * 最近会话 分页查询
 *
 * 根据当前页面 最下面一条的 MsgId查询
 * 包含联系人和群组
 *
 * @author: 12
 * @create: 2019-12-27 17:10
 **/
@Handler(req = Req.SESSION_LIST)
public class SessionMsgHandler implements IHandler<MsgPageDTO> {

  @Autowired
  ISessionService sessionService;

  @Override
  public Object handle(ReqContent req, MsgPageDTO data) {

    return sessionService.sessionList(req.getUserId(),data);
  }




}
