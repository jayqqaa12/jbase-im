package com.jayqqaa12.im.gateway.protool.route;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.jayqqaa12.im.common.model.tcp.Route;
import com.jayqqaa12.im.common.model.tcp.Router;
import com.jayqqaa12.im.common.model.tcp.TcpContext;
import com.jayqqaa12.im.common.model.consts.Req;
import com.jayqqaa12.im.common.model.vo.TcpReqVO;
import com.jayqqaa12.im.gateway.support.SendHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * 收到ack消息 改变消息状态
 *
 * 批量操作
 *
 * @Deprecated 优化为批量ack
 * @author: 12
 * @create: 2019-09-11 11:11
 **/
@Route(req = Req.MSG_ACK  )
public class MsgAckRoute implements Router<JSONObject> {

    @Autowired
    SendHelper sendHelper;

    @Override
    public void handle(TcpContext context, TcpReqVO req, JSONObject data) throws Exception {
        Long msgId= data.getLong("msgId");

        Assert.notNull(msgId, "msg id can't null");

        //停止重发
        sendHelper.removeRetryMsg(msgId);


    }

}
