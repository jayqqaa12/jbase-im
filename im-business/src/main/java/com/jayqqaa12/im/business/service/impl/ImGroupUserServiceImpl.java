package com.jayqqaa12.im.business.service.impl;

import com.jayqqaa12.im.common.model.entity.ImGroupUser;
import com.jayqqaa12.im.business.mapper.ImGroupUserMapper;
import com.jayqqaa12.im.business.service.IGroupUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 
群用户表 并且存了 最后拉取群消息的 msg id  用来拉取离线消息 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Service
public class ImGroupUserServiceImpl extends ServiceImpl<ImGroupUserMapper, ImGroupUser> implements IGroupUserService {

}
