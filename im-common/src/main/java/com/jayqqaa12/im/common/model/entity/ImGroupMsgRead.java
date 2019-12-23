package com.jayqqaa12.im.common.model.entity;

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
public class ImGroupMsgRead implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long gMsgId;

    private Long sendUid;

    private Long recvUid;

    private Long gid;

    private Boolean read;


}
