package com.jayqqaa12.im.common.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
群用户表 并且存了 最后拉取群消息的 msg id  用来拉取离线消息
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImGroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long uid;

    private Long gid;

    private LocalDateTime createTime;


}
