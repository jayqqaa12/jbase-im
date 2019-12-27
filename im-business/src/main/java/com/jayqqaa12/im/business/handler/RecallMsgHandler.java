package com.jayqqaa12.im.business.handler;

import com.jayqqaa12.im.business.model.consts.SessionType;
import com.jayqqaa12.im.business.model.dto.RecallMsgDTO;
import com.jayqqaa12.im.business.model.entity.ImMsg;
import com.jayqqaa12.im.business.model.entity.ImOfflineInstruct;
import com.jayqqaa12.im.business.service.IMsgService;
import com.jayqqaa12.im.business.service.IOfflineInstructService;
import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.client.SendClient;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.common.model.consts.Resp;
import com.jayqqaa12.im.common.model.dto.RpcDTO;
import com.jayqqaa12.im.common.model.vo.TcpRespVO;
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
public class RecallMsgHandler implements IHandler<RecallMsgDTO> {

  @Autowired
  IMsgService msgService;

  @Autowired
  IOfflineInstructService offlineInstructService;


  @Autowired
  SendClient sendClient;


  @Override
  public Object handle(RpcDTO req, RecallMsgDTO data) {

    //超过 x分钟不能撤回
    LocalDateTime time = LocalDateTime.now().minusMinutes(3);

    if (SessionType.SINGLE.equals(data.getSessionType())) {
      ImMsg msg = msgService.getById(data.getMsgId());
      if (time.isBefore(msg.getCreateTime()))
        throw new BusinessException(Resp.RECALL_TIMEOUT);

      msgService.updateById(new ImMsg().setId(msg.getId()).setRecall(true));

      //给消息的接受者发撤回
      send(req.getCode(), msg.getRecvUid(), data);
      //给消息的发送者也要发，因为可能是多平台
      send(req.getCode(), req.getUserId(), data);
    }

    return data.getMsgId();
  }


  private void send(int code, Long uid, RecallMsgDTO data) {

    //存离线指令 给其他没有上线的客户端使用
    ImOfflineInstruct offlineInstruct = new ImOfflineInstruct()
      .setUid(uid).
        setContent(TcpRespVO.response(code, data, uid.toString()));

    offlineInstructService.save(offlineInstruct);

    sendClient.send(uid.toString(), offlineInstruct.getContent().setRespId(offlineInstruct.getId()));
  }
}
