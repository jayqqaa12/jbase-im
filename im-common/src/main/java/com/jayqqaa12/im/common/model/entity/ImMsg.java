package com.jayqqaa12.im.common.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 消息类型 0 文字 1.表情2图片3,语音4视频 5 json
     */
    private Integer type;

    /**
     * 是否撤回
     */
    private Boolean recall;

    private Long recvUid;

    private Long sendUid;

    /**
     * 是否已读
     */
    private Boolean read;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
