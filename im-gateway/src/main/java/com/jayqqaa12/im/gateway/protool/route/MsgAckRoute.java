package com.jayqqaa12.im.gateway.protool.route;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayqqaa12.im.gateway.protool.base.Route;
import com.jayqqaa12.im.gateway.protool.base.Router;
import com.jayqqaa12.im.gateway.protool.base.TcpContext;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.gateway.protool.model.vo.TcpReqVO;
import com.jayqqaa12.im.gateway.support.SendHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 收到ack消息 改变消息状态
 * <p>
 * 批量操作
 *
 * @author: 12
 * @create: 2019-09-11 11:11
 **/
@Route(req = Req.MSG_ACK)
public class MsgAckRoute implements Router<JSONObject> {

  @Autowired
  SendHelper sendHelper;

  @Override
  public void handle(TcpContext context, TcpReqVO req, JSONObject data) throws Exception {
    JSONArray msgIds = data.getJSONArray("msgIds");

    Assert.notNull(msgIds, "msg id can't null");

    //停止重发
    for (int i = 0; i < msgIds.size(); i++) {
      sendHelper.removeRetryMsg(msgIds.getLong(i));
    }


  }

}
