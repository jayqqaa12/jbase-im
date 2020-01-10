package com.jayqqaa12.im.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayqqaa12.im.business.mapper.ImSessionMapper;
import com.jayqqaa12.im.business.model.consts.SessionType;
import com.jayqqaa12.im.business.model.dto.MsgPageDTO;
import com.jayqqaa12.im.business.model.entity.ImMsg;
import com.jayqqaa12.im.business.model.entity.ImSession;
import com.jayqqaa12.im.business.model.vo.SessionVO;
import com.jayqqaa12.im.business.service.ISessionService;
import com.jayqqaa12.im.business.support.CountHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * <p>
 * <p>
 * 最新的会话的 索引 只记录最新一条消息 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Service
public class ImSessionServiceImpl extends ServiceImpl<ImSessionMapper, ImSession> implements ISessionService {

  @Autowired
  CountHelper countHelper;

  @Override
  public void saveSession(ImMsg msg, SessionType type) {

    saveSession(msg, type, msg.getSendUid(), msg.getRecvUid());
    saveSession(msg, type, msg.getRecvUid(), msg.getSendUid());
  }

  @Override
  public Object sessionList(Long userId, MsgPageDTO data) {
    return page(new Page<>(1, data.getSize()),
      new LambdaQueryWrapper<ImSession>().eq(ImSession::getUid, userId)
        .gt(ImSession::getLastMsgId, data.getLastMsgId())
        .orderByDesc(ImSession::getLastMsgId)
    ).getRecords().stream().map((s) -> {
      SessionVO vo = new SessionVO();
      BeanUtils.copyProperties(s, vo);
      vo.setMsgCount(countHelper.get(userId, s.getSessionId()));
      return vo;
    }).collect(Collectors.toList());
  }


  private void saveSession(ImMsg msg, SessionType type, Long uid, Long sessionId) {
    ImSession session = new ImSession().setMsg(msg).setLastMsgId(msg.getId());

    boolean rst = update(session, new UpdateWrapper<>(new ImSession().setUid(uid)
      .setSessionId(sessionId)));
    //不存在 插入
    if (!rst) {
      try {
        this.save(session.setUid(msg.getSendUid()).setType(type).setSessionId(msg.getRecvUid()));
      } catch (Exception e) {
        //可能导致并发插入唯一主键冲突 忽略
      }
    }
  }
}
