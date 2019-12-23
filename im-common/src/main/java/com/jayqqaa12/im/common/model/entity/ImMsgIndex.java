package com.jayqqaa12.im.common.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 单聊的 消息索引 （新增一条消息 存2条  ）
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImMsgIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long msgId;

    private Long uid;

    private Long otherUid;

    /**
     * 0 send 1 recv
     */
    private Boolean type;


}
