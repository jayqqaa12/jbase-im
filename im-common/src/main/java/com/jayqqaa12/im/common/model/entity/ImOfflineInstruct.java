package com.jayqqaa12.im.common.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
记录离线的指令 （指令可能是删除消息，撤回消息之类的）
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImOfflineInstruct implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long uid;

    private String content;


}
