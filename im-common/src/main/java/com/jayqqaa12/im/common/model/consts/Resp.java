package com.jayqqaa12.im.common.model.consts;

public interface Resp {


    int OK=200;
    int HEART = 300;//心跳
    int ERROR = 500; // 未知错误

    
    int CMD_ERROR = 2000; // cmd不能找到
    int PARAM_ERROR = 2001; // 参数异常.

    //业务异常 （2100+
    int UNLOGIN = 2101; // 用户没有登录
    
    int RECALL_TIMEOUT=2102;//撤回超时
    int RETRY_RECALL_ERROR = 2103;    //重复撤回错误
    int TOKEN_ERROR=2104;//登录异常

    

}