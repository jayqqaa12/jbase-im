package com.jayqqaa12.im.business.service;

import com.jayqqaa12.im.business.model.consts.SessionType;
import com.jayqqaa12.im.business.model.dto.MsgPageDTO;
import com.jayqqaa12.im.business.model.entity.ImMsg;
import com.jayqqaa12.im.business.model.entity.ImSession;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *
最新的会话的 索引 只记录最新一条消息 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
public interface ISessionService extends IService<ImSession> {

    void saveSession(ImMsg msg, SessionType single);

  Object sessionList(Long userId, MsgPageDTO data);
}
