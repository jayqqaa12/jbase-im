//package com.jayqqaa12.im.util;
//
//import com.alibaba.fastjson.JSON;
//import com.gexin.rp.sdk.base.IPushResult;
//import com.gexin.rp.sdk.base.impl.AppMessage;
//import com.gexin.rp.sdk.base.impl.ListMessage;
//import com.gexin.rp.sdk.base.impl.SingleMessage;
//import com.gexin.rp.sdk.base.impl.Target;
//import com.gexin.rp.sdk.base.notify.Notify;
//import com.gexin.rp.sdk.base.payload.APNPayload;
//import com.gexin.rp.sdk.dto.GtReq;
//import com.gexin.rp.sdk.exceptions.RequestException;
//import com.gexin.rp.sdk.http.IGtPush;
//import com.gexin.rp.sdk.template.NotificationTemplate;
//import com.gexin.rp.sdk.template.TransmissionTemplate;
//import com.gexin.rp.sdk.template.style.Style0;
//import com.google.gateway.collect.Lists;
//import com.jayqqaa12.im.common.model.PushModel;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.*;
//
//@Slf4j
//public class PushUtils {
//    private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
//    private static String url = "http://sdk.open.api.igexin.com/serviceex";
//    private static Integer MAX_TARGET = 100;   //每次推送的用户数量
//
//
//    /**
//     * 推送消息给安卓用户
//     *
//     * @param pushModel    推送数据
//     * @param appId
//     * @param appKey
//     * @param masterSecret
//     * @return
//     */
//    public static String sendSingle(PushModel pushModel, String appId, String appKey, String masterSecret) {
//        /**
//         * 获取设备身份
//         */
//        String cid = pushModel.getDevice();
//
//
//        IGtPush push = new IGtPush(host, appKey, masterSecret);
//        try {
//            //记录日志
//            log.info("推送开始,设备={},内容={}", cid == null ? pushModel.getAlias() : cid, pushModel.getData());
//            //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate
//            // 1.TransmissionTemplate:透传功能模板
//            // 2.LinkTemplate:通知打开链接功能模板
//            // 3.NotificationTemplate：通知透传功能模板
//            // 4.NotyPopLoadTemplate：通知弹框下载功能模板
//            TransmissionTemplate template = getTemplate(pushModel, appId, appKey);
//            //接收者
//            Target target = new Target();
//            //接收者安装的应用的APPID
//            target.setAppId(appId);
//            //接收者的ClientID
//            target.setClientId(cid);
//            if (StringUtils.isEmpty(cid)) target.setAlias(pushModel.getAlias());
//            //单推消息类型
//            SingleMessage message = new SingleMessage();
//            //用户当前不在线时，是否离线存储,可选
//            message.setOffline(true);
//            //离线有效时间，单位为毫秒，可选
//            message.setOfflineExpireTime(1000 * 60 * 60);
//            message.setData(template);
//            //单推
//            IPushResult ret = push.pushMessageToSingle(message, target);
//            log.info("推送结束,设备={},返回={}", cid == null ? pushModel.getAlias() : cid, ret.getResponse());
//            return ret.getResponse().toString();
//
//        } catch (RequestException ex) {
//            log.warn("发送出错 ex={}", ex);
//            return "";
//        } catch (Exception ex) {
//            log.warn("发送出错 ex={}", ex);
//            return "";
//        }
//    }
//
//
//
//    /**
//     * 单传模板
//     *
//     * @param model
//     * @param appId
//     * @param appKey
//     * @return
//     * @throws Exception
//     */
//    private static TransmissionTemplate getTemplate(PushModel model, String appId,
//                                                    String appKey) throws Exception {
//        TransmissionTemplate template = new TransmissionTemplate();
//        template.setAppId(appId);
//        template.setAppkey(appKey);
//
//        //这个是给android 用的
//        String title = model.getTitle();
//        String msg = model.getMsg();
//        Map<String, Object> map = new HashMap<>();
//        map.put("title", title);
//        map.put("msg", msg);
////        map.put("userInfo", model.getUserInfo());
//        map.put("data", model.getData());
//        map.put("type", model.getType());
//        map.put("classify", model.getClassify());
//        map.put("createTime", System.currentTimeMillis());
////        map.put("orderName", model.getOrderName());
//        map.put("url", model.getUrl());
//        template.setTransmissionType(2);
//        template.setTransmissionContent(JSON.toJSONString(map));
//
//
//
//        Notify notify = new Notify();
//        notify.setTitle(model.getTitle());
//        notify.setContent(msg);
//        notify.setIntent("intent:#Intent;package=com.deer.jayqqaa12;component=com.deer.jayqqaa12/.ui.MainActivity;S.msgJson="+JSON.toJSONString(map)+";i.msgType=1;end");
//        notify.setType(GtReq.NotifyInfo.Type._intent);
//        template.set3rdNotifyInfo(notify);
//
//
//
//        //这个是给ios 用的
//        APNPayload payload = new APNPayload();
//        //payload.setBadge(1);
////        payload.setContentAvailable(1);
//        payload.setSound("default");
//
//        payload.addCustomMsg("title", title);
//        payload.addCustomMsg("msg", msg);
////        payload.addCustomMsg("userInfo", model.getUserInfo());
//        payload.addCustomMsg("data", model.getData());
//        payload.addCustomMsg("type", model.getType());
//        payload.addCustomMsg("classify", model.getClassify());
//        payload.addCustomMsg("createTime", System.currentTimeMillis());
//        payload.addCustomMsg("url", model.getUrl());
////        payload.addCustomMsg("orderName", model.getOrderName());
//        // payload.setCategory("$由客户端定义")
//        // 字典模式使用下者
//        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
//        //alertMsg.setActionLocKey(JSON.toJSONString(model.getData()));
//        //alertMsg.setLocKey(JSON.toJSONString(model.getData()));
//        //alertMsg.addLocArg(JSON.toJSONString(model.getData()));
//
//        alertMsg.setBody(msg);
//        alertMsg.setTitle(title);
//        payload.setAlertMsg(alertMsg);
//        template.setAPNInfo(payload);
//        return template;
//    }
//
//    public static NotificationTemplate getTemplateForList(PushModel model, String appId, String appKey) throws Exception {
//        NotificationTemplate template = new NotificationTemplate();
//        // 设置APPID与APPKEY
//        template.setAppId(appId);
//        template.setAppkey(appKey);
//
//        Style0 style = new Style0();
//        // 设置通知栏标题与内容
//        style.setTitle("请输入通知栏标题");
//        style.setText("请输入通知栏内容");
//        // 配置通知栏图标
//        // style.setLogo("icon.png");
//        // 配置通知栏网络图标
//        //style.setLogoUrl("");
//        // 设置通知是否响铃，震动，或者可清除
//        style.setRing(true);
//        style.setVibrate(true);
//        style.setClearable(true);
//        template.setStyle(style);
//
//        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
//        template.setTransmissionType(2);
//        template.setTransmissionContent(model.getMsg());
//        return template;
//    }
//
//    public static void sendAll(PushModel pushModel, String appId, String appkey, String master) throws Exception {
//        IGtPush push = new IGtPush(url, appkey, master);
//
//        AppMessage message = new AppMessage();
//        message.setData(getTemplate(pushModel, appId, appkey));
//        message.setAppIdList(Lists.newArrayList(appId));
//        message.setOffline(true);
//        message.setOfflineExpireTime(1000 * 600);
//
//        IPushResult ret = push.pushMessageToApp(message);
//
//        log.info("推送全部设备的结果 {}", ret.getResponse());
//    }
//
//    public static void sendToList(PushModel pushModel, String appId, String appKey, String masterSecret) {
//        IGtPush push = new IGtPush(host, appKey, masterSecret);
//        try {
//            Set<String> list = pushModel.getDeviceList();
//            if (list.size() <= 0) {
//                log.info("没有要推送的用户群");
//                return;
//            }
//            Iterator iterator = list.iterator();
//            List<String> newList = new ArrayList<>();
//            Integer count = 0;
//            boolean flag;
//            do {
//                if (count != 0 && count % MAX_TARGET == 0 && newList.size() > 0) {
//                    flag = true;
//                } else {
//                    newList.add((String) iterator.next());
//                    flag = list.size() - count < MAX_TARGET && count == list.size() - 1;
//                    count++;
//                }
//                if (flag) {
//                    log.info("skip:{}, limit:{}", count, MAX_TARGET);
//                    ListMessage message = new ListMessage();
//                    message.setData(getTemplate(pushModel, appId, appKey));
//                    message.setOffline(true);
//                    message.setOfflineExpireTime(1000 * 600);
//                    List<Target> targets = new ArrayList<>();
//
//                    if (!pushModel.getDeviceList().isEmpty()) {
//                        for (String deviceId : newList) {
//                            Target target = new Target();
//                            if (StringUtils.isNotEmpty(deviceId)) {
//                                target.setAppId(appId);
//                                target.setClientId(deviceId);
//                                targets.add(target);
//                            }
//                        }
//                    }
//
//                    if (!pushModel.getAliasList().isEmpty()) {
//                        for (String alias : newList) {
//                            Target target = new Target();
//                            if (StringUtils.isNotEmpty(alias)) {
//                                target.setAppId(appId);
//                                target.setAlias(alias);
//                                targets.add(target);
//                            }
//                        }
//                    }
//
//
//                    if (targets.size() <= 0) {
//                        log.info("没有要推送的用户群");
//                        return;
//                    }
//                    String taskId = push.getContentId(message);
//                    IPushResult ret = push.pushMessageToList(taskId, targets);
//                    log.info("推送指定用户群的结果 {}", ret.getResponse());
//                    newList.clear();
//                }
//            }
//            while (iterator.hasNext());
//        } catch (Exception ex) {
//            log.warn("推送指定用户群出错 {} ", pushModel.getDeviceList(), ex);
//        }
//    }
//}