package com.jayqqaa12.im.business.service;

import com.jayqqaa12.im.common.model.entity.ImGroupUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 
群用户表 并且存了 最后拉取群消息的 msg id  用来拉取离线消息 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
public interface IGroupUserService extends IService<ImGroupUser> {

}
