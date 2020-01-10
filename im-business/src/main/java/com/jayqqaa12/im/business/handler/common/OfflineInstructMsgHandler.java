package com.jayqqaa12.im.business.handler.common;

import com.jayqqaa12.im.business.model.dto.MsgPageDTO;
import com.jayqqaa12.im.business.service.IOfflineInstructService;
import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.model.ReqContent;
import com.jayqqaa12.im.common.model.consts.Req;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * 离线指令只要是指 消息删除 消息已读等指令
 *
 * 应用上线的时候拉取 来更新消息状态 或事件
 *
 * 本地存最新获取的离线指令id  根据id 返回离线指令
 *
 * @author: 12
 * @create: 2019-12-27 17:10
 **/
@Handler(req = Req.OFFLINE_INSTRUCT)
public class OfflineInstructMsgHandler implements IHandler<MsgPageDTO> {

  @Autowired
  IOfflineInstructService instructService;

  @Override
  public Object handle(ReqContent req, MsgPageDTO data) {

    return instructService.offlineList(req.getUserId(),data.getLastMsgId());

  }


}
