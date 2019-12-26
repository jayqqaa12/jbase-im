package com.jayqqaa12.im.business.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImGroupMsgDelete implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long gMsgId;

    private Long uid;

    private Long gid;


}
