package com.jayqqaa12.im.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayqqaa12.im.business.mapper.ImMsgIndexMapper;
import com.jayqqaa12.im.business.mapper.ImMsgMapper;
import com.jayqqaa12.im.business.model.consts.MsgIndexType;
import com.jayqqaa12.im.business.model.consts.SessionType;
import com.jayqqaa12.im.business.model.dto.MsgListDTO;
import com.jayqqaa12.im.business.model.entity.ImMsg;
import com.jayqqaa12.im.business.model.entity.ImMsgIndex;
import com.jayqqaa12.im.business.service.IMsgService;
import com.jayqqaa12.im.business.service.ISessionService;
import com.jayqqaa12.im.business.support.CountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Service
public class ImMsgServiceImpl extends ServiceImpl<ImMsgMapper, ImMsg> implements IMsgService {

  @Autowired
  ImMsgIndexMapper msgIndexMapper;


  @Autowired
  ISessionService sessionService;


  @Autowired
  CountHelper countHelper;

  @Override
  @Transactional
  public void saveMsg(ImMsg msg) {
    this.save(msg);
    //save msg index
    msgIndexMapper.insert(new ImMsgIndex()
      .setMsgId(msg.getId()).setUid(msg.getSendUid())
      .setOtherUid(msg.getRecvUid()).setType(MsgIndexType.SEND));
    msgIndexMapper.insert(new ImMsgIndex()
      .setMsgId(msg.getId()).setUid(msg.getRecvUid())
      .setOtherUid(msg.getSendUid()).setType(MsgIndexType.RECV));

    //update msg count
    countHelper.increment(msg.getSendUid(),msg.getRecvUid());
    countHelper.increment(msg.getRecvUid(),msg.getSendUid());


    //update session
    sessionService.saveSession(msg, SessionType.SINGLE);

  }


  @Override
  public void deleteMsg(Long msgId, Long uid) {

    //删除索引就表示删除了消息
    msgIndexMapper.delete(new UpdateWrapper<>(new ImMsgIndex()
      .setUid(uid).setMsgId(msgId)));
  }

  /**
   * 历史消息拉取收和发的
   *
   * 从后往前拉
   * 拉取小于  lastMsgId的消息 (lastMsgId从会话列表获取)
   *
   * @param userId
   * @param data
   * @return
   */
  @Override
  public List<ImMsg> historyList(Long userId, MsgListDTO data) {

    List<ImMsgIndex> list = msgIndexMapper.selectPage(new Page<>(1, data.getSize()), new LambdaQueryWrapper<ImMsgIndex>()
      .lt(ImMsgIndex::getMsgId, data.getLastMsgId())
      .eq(ImMsgIndex::getOtherUid, data.getSessionId())
      .eq(ImMsgIndex::getUid, userId)

      .orderByDesc(ImMsgIndex::getMsgId)
    ).getRecords();

    return list.stream().map((index) -> this.getById(index.getMsgId())).collect(Collectors.toList());
  }

}
