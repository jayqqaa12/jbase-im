package com.jayqqaa12.im.common.model.consts;

/**
 * 上行
 */
public interface Req {
  int HEART = 300;  //心跳
  int MSG_ACK = 201;             //消息确认

  int UNREGISTER = 1001;      //取消注册
  int REGISTER = 1000;     //注册


  //业务请求码 (1100,2000】

  int BUSINESS = 1100;


  //通用消息  (1101-1200】

  int CLEAR_COUNT = 1102; //清空计数
  int ONLINE_STATUS = 1103;//在线状态查看
  int MSG_COUNT = 1104;   //获取消息数量

  int SESSION_LIST = 1110;// 最近会话列表

  int OFFLINE_INSTRUCT=1111;//离线指令获取


  // 单聊 （1201-1300】
  int INPUTTING = 1203;    // 用户正在输入

  int HISTORY_MSG_LIST = 1202;//查询历史聊天记录
  int READ_MSG = 1205;// 通知消息已读
  int SEND_MSG = 1206;//发消息
  int RECALL_MSG = 1207;    // 撤回消息
  int DELETE_MSG = 1208;//删除消息


  //群聊  （1301-1400】


}

