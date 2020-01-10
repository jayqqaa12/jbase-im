package com.jayqqaa12.im.business.handler.single;

import com.jayqqaa12.im.business.model.dto.MsgStatusDTO;
import com.jayqqaa12.im.business.model.entity.ImMsg;
import com.jayqqaa12.im.business.service.IMsgService;
import com.jayqqaa12.im.business.service.IOfflineInstructService;
import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.model.ReqContent;
import com.jayqqaa12.im.common.model.consts.Req;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 已读消息
 *
 * 修改消息状态  接收方不在线 存离线指令
 *
 * @author: 12
 * @create: 2019-12-27 17:10
 **/
@Handler(req = Req.READ_MSG)
public class ReadlMsgHandler implements IHandler<MsgStatusDTO> {

  @Autowired
  IMsgService msgService;

  @Autowired
  IOfflineInstructService instructService;

  @Override
  public Object handle(ReqContent req, MsgStatusDTO data) {

      ImMsg msg = msgService.getById(data.getMsgId());

      msgService.updateById(new ImMsg().setId(msg.getId()).setRead(true));

      //给消息的接受者发已读
      instructService.send(req.getCode(), msg.getRecvUid(), data);
      //给消息的发送者也要发，因为可能是多平台
      instructService.send(req.getCode(), req.getUserId(), data);

    return data.getMsgId();
  }


}
