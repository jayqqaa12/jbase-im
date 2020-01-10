package com.jayqqaa12.im.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayqqaa12.im.business.mapper.ImOfflineInstructMapper;
import com.jayqqaa12.im.business.model.dto.MsgStatusDTO;
import com.jayqqaa12.im.business.model.entity.ImOfflineInstruct;
import com.jayqqaa12.im.business.service.IOfflineInstructService;
import com.jayqqaa12.im.common.client.SendClient;
import com.jayqqaa12.im.common.model.vo.TcpRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * <p>
 * 记录离线的指令 （指令可能是删除消息，撤回消息之类的） 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Service
public class ImOfflineInstructServiceImpl extends ServiceImpl<ImOfflineInstructMapper, ImOfflineInstruct> implements IOfflineInstructService {


  @Autowired
  SendClient sendClient;


  @Override
  public void send(Integer code, Long uid, MsgStatusDTO data) {
    long id = IdWorker.getId();

    //存离线指令 给其他没有上线的客户端使用
    ImOfflineInstruct offlineInstruct = new ImOfflineInstruct()
      .setUid(uid)
      .setId(id)
      .setContent(TcpRespVO.response(code, data, uid.toString(), id));

    this.save(offlineInstruct);

    sendClient.send(offlineInstruct.getContent());
  }


  @Override
  public List<ImOfflineInstruct> offlineList(Long userId, Long lastMsgId) {
    return this.list(new LambdaQueryWrapper<ImOfflineInstruct>()
      .eq(ImOfflineInstruct::getUid,userId)
      .gt(ImOfflineInstruct::getId, lastMsgId)
      .orderByDesc(ImOfflineInstruct::getId)
    );
  }


}
