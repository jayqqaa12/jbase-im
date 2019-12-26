package com.jayqqaa12.im.business.model.entity;

import com.jayqqaa12.im.business.model.consts.SessionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
最新的会话的 索引 只记录最新一条消息
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 最后一条消息内容 json存储
     */
    private String msg;

    private Long uid;

    /**
     * 单聊是另一个用户的uid 群聊是gid
     */
    private Long sessionId;

    /**
     * 0 单聊 1 群聊
     */
    private SessionType type;

    /**
     * 最后ack的消息id
     */
    private Long lastAckMsgId;


}
