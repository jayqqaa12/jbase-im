//package com.jayqqaa12.im.support;
//
//import com.jayqqaa12.im.common.model.PushModel;
//import com.jayqqaa12.im.common.model.vo.TcpRespVO;
//import com.jayqqaa12.im.util.PushUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///**
// * 个推支持
// *
// * @author: 12
// * @create: 2019-09-25 13:29
// **/
//@Component
//public class PushHelper {
//
//    @Value("${push.getui.appid}")
//    String appid;
//    @Value("${push.getui.appkey}")
//    String appkey;
//    @Value("${push.getui.secret}")
//    String secret;
//
//
//    public void sendMsg(TcpRespVO data) {
//        if (data.getDest() == null) return;
//
//        PushModel pushModel = new PushModel();
//        pushModel.setAlias(data.getDest());
//        pushModel.setTitle("你有新的消息");
//
//        pushModel.setType(1);
//
//
//
//        pushModel.setMsg("你收到了一条消息");
//        pushModel.setData(data);
//
//        PushUtils.sendSingle(pushModel, appid, appkey, secret);
//    }
//
//
//}
