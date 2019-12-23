package com.jayqqaa12.im.common.model.consts;

/**
 * 上行
 */
public interface Req {
    int HEART = 300;  //心跳
    int MSG_ACK = 201;             //消息确认

    int UNREGISTER = 1001;      //取消注册
    int REGISTER = 1000;     //注册

    
    //业务请求码 (1100-2000】

    int GET_OFFLINE_MSG = 1102;            //离线消息
    int GET_ONLINE_STATUS = 1103;//在线状态查看

    int GET_MSG_COUNT=1104;   //获取消息数量

    int READ_MSG =1105;// 消息已读

    int SEND_MSG =1106;//发消息

    int RECALL_MSG = 1107;    // 撤回消息

    int INPUTTING = 1108;    // 用户正在输入

    int GET_MSG_READ_STATUS = 1109; //查询消息状态

    int CLEAR_COUNT=1110; //清空计数
    
    int GET_HIS_MSG = 1111;//查询历史聊天记录


}

