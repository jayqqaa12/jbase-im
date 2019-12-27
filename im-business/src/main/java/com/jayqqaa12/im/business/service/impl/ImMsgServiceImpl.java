package com.jayqqaa12.im.business.service.impl;

import com.jayqqaa12.im.business.mapper.ImMsgIndexMapper;
import com.jayqqaa12.im.business.model.consts.MsgIndexType;
import com.jayqqaa12.im.business.model.entity.ImMsg;
import com.jayqqaa12.im.business.mapper.ImMsgMapper;
import com.jayqqaa12.im.business.model.entity.ImMsgIndex;
import com.jayqqaa12.im.business.service.IMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  }
}
