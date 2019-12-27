package com.jayqqaa12.im.business.handler;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jayqqaa12.im.business.model.consts.SessionType;
import com.jayqqaa12.im.business.model.dto.SendMsgDTO;
import com.jayqqaa12.im.business.model.entity.ImMsg;
import com.jayqqaa12.im.business.service.IMsgService;
import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.client.SendClient;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.common.model.consts.Resp;
import com.jayqqaa12.im.common.model.dto.RpcDTO;
import com.jayqqaa12.jbase.spring.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 接受消息  ->保存数据库 ->返回发送者msgid  -》发送给对方
 *
 * @author: 12
 * @create: 2019-12-27 14:08
 **/
@Handler(req = Req.SEND_MSG)
public class MsgHandler implements IHandler<SendMsgDTO> {

  @Autowired
  IMsgService msgService;

  @Autowired
  SendClient sendClient;

  @Override
  public Object handle(RpcDTO req, SendMsgDTO data) {
    //单聊

    if (SessionType.SINGLE.equals( data.getSessionType()) ) {
      ImMsg msg = new ImMsg();
      //收到消息就创建id 保存到数据库的时候再创建可能有一定延迟 更大限度保证消息的有序性
      msg.setId(IdWorker.getId());
      BeanUtils.copyProperties(data, msg);
      msg.setSendUid(req.getUserId());
      msgService.saveMsg(msg);

      // 推送给接受用户
      sendClient.send(data.getRecvUid().toString(), req.getCode(),msg);

      // 推送给发送用户 因为可能存在多个平台
      sendClient.send(req.getUserId().toString(), req.getCode(),msg);


      return msg.getId();
    }
    else{
      //todo 群聊
      throw new BusinessException(Resp.CMD_ERROR);

    }


  }
}
