package com.jayqqaa12.im.business.mapper;

import com.jayqqaa12.im.common.model.entity.ImGroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 
群用户表 并且存了 最后拉取群消息的 msg id  用来拉取离线消息 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
public interface ImGroupUserMapper extends BaseMapper<ImGroupUser> {

}
