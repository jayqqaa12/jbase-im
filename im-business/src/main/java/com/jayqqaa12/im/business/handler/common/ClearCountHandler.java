package com.jayqqaa12.im.business.handler.common;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.jayqqaa12.im.business.support.CountHelper;
import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.client.SendClient;
import com.jayqqaa12.im.common.model.ReqContent;
import com.jayqqaa12.im.common.model.consts.Req;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * 清空未读数量
 *
 * @author: 12
 * @create: 2019-12-27 17:04
 **/
@Handler(req = Req.CLEAR_COUNT)
public class ClearCountHandler implements IHandler<JSONObject> {

  @Autowired
  CountHelper countHelper;

  @Autowired
  SendClient sendClient;

  @Override
  public Object handle(ReqContent req, JSONObject data) {

    Long sessionId= data.getLong("sessionId");
    Assert.notNull(sessionId, "sessionId can't null");
    countHelper.reset(req.getUserId(),sessionId);

    //通知自己的其他客户端也清空消息数量
    sendClient.send(req.getUserId()+"", req.getCode(), data);

    return null;
  }
}
