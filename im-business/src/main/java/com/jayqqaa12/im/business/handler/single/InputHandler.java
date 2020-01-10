package com.jayqqaa12.im.business.handler.single;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.jayqqaa12.im.business.support.Handler;
import com.jayqqaa12.im.business.support.IHandler;
import com.jayqqaa12.im.common.client.SendClient;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.common.model.ReqContent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 正在输入中 转发即可
 *
 * @author: 12
 * @create: 2019-12-27 17:04
 **/
@Handler(req = Req.INPUTTING)
public class InputHandler implements IHandler<JSONObject> {

  @Autowired
  SendClient sendClient;

  @Override
  public Object handle(ReqContent req, JSONObject data) {

    String dest = data.getString("dest");
    Assert.notNull(dest, "dest can't null");

    sendClient.send(dest, req.getCode(), null);

    return null;
  }
}
