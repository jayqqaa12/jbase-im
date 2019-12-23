package com.jayqqaa12.im.business.service.impl;

import com.jayqqaa12.im.common.model.entity.ImSession;
import com.jayqqaa12.im.business.mapper.ImSessionMapper;
import com.jayqqaa12.im.business.service.ISessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 
最新的会话的 索引 只记录最新一条消息 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Service
public class ImSessionServiceImpl extends ServiceImpl<ImSessionMapper, ImSession> implements ISessionService {

}
