package com.jayqqaa12.im.business.model.entity;

import com.jayqqaa12.im.business.model.consts.MsgType;
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
public class ImGroupMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 消息类型 0 文字 1.表情2图片3,语音4视频 5 json
     */
    private MsgType type;

    /**
     * 是否撤回
     */
    private Boolean recall;

    private Long sendUid;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long gid;


}
