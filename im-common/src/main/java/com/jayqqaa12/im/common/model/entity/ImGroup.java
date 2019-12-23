package com.jayqqaa12.im.common.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
群组表
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long createUid;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
