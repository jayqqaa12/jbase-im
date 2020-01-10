package com.jayqqaa12.im.business.handler.single;

import com.jayqqaa12.im.business.model.dto.MsgListDTO;
import com.jayqqaa12.im.business.service.IMsgService;
import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.model.ReqContent;
import com.jayqqaa12.im.common.model.consts.Req;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 历史消息 分页查询
 *
 * 因为存在消息漫游 所以要双方发的消息都要显示出来
 *
 * 直接根据索引表关联查询
 *
 * @author: 12
 * @create: 2019-12-27 17:10
 **/
@Handler(req = Req.HISTORY_MSG_LIST)
public class HistoryMsgHandler implements IHandler<MsgListDTO> {

  @Autowired
  IMsgService msgService;

  @Override
  public Object handle(ReqContent req, MsgListDTO data) {

    return msgService.historyList(req.getUserId(), data);
  }


}
