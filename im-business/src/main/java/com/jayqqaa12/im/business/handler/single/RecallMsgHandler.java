package com.jayqqaa12.im.business.handler.single;

import com.jayqqaa12.im.business.model.dto.MsgStatusDTO;
import com.jayqqaa12.im.business.model.entity.ImMsg;
import com.jayqqaa12.im.business.service.IMsgService;
import com.jayqqaa12.im.business.service.IOfflineInstructService;
import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.model.ReqContent;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.common.model.consts.Resp;
import com.jayqqaa12.jbase.spring.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * 撤回消息
 * <p>
 * 修改消息状态 发送给所有相关用户（包括自己）
 * <p>
 * 可能有客户端不在线 存离线指令
 *
 * @author: 12
 * @create: 2019-12-27 17:10
 **/

@Handler(req = Req.RECALL_MSG)
public class RecallMsgHandler implements IHandler<MsgStatusDTO> {

  @Autowired
  IMsgService msgService;

  @Autowired
  IOfflineInstructService instructService;

  @Override
  public Object handle(ReqContent req, MsgStatusDTO data) {

    //超过 x分钟不能撤回
    LocalDateTime time = LocalDateTime.now().minusMinutes(3);

      ImMsg msg = msgService.getById(data.getMsgId());
      if (time.isBefore(msg.getCreateTime()))
        throw new BusinessException(Resp.RECALL_TIMEOUT);

      msgService.updateById(new ImMsg().setId(msg.getId()).setRecall(true));

      //给消息的接受者发撤回
      instructService.send(req.getCode(), msg.getRecvUid(), data);
      //给消息的发送者也要发，因为可能是多平台
      instructService.send(req.getCode(), req.getUserId(), data);

    return data.getMsgId();
  }



}
