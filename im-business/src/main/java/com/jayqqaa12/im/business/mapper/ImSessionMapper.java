package com.jayqqaa12.im.business.mapper;

import com.jayqqaa12.im.common.model.entity.ImSession;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 
最新的会话的 索引 只记录最新一条消息 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
public interface ImSessionMapper extends BaseMapper<ImSession> {

}
