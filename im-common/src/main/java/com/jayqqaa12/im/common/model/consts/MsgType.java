package com.jayqqaa12.im.common.model.consts;

/**
 * @author: 12
 * @create: 2019-09-10 16:41
 **/
public enum MsgType {

    /**
     * 文字
     */
    TEXT,
    /**
     * 表情
     */
    EXPRESSION,
    /**
     * 图片
     */
    IMG,
    /**
     * 语音
     */
    VOICE,

    /**
     * 视频
     */
    VIDEO,

    /**
     * 业务场景不同而不同json
     *
     */
    JSON,

    ;

    public static MsgType getMsgTypeByOrdinal(Integer ordinal){
        for (MsgType msgType:MsgType.values()){
            if(msgType.ordinal()==ordinal){
                return msgType;
            }
        }
        return null;
    }

}
