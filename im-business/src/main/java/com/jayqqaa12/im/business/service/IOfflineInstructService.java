package com.jayqqaa12.im.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jayqqaa12.im.business.model.dto.MsgStatusDTO;
import com.jayqqaa12.im.business.model.entity.ImOfflineInstruct;

import java.util.List;

/**
 * <p>
 * <p>
 * 记录离线的指令 （指令可能是删除消息，撤回消息之类的） 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
public interface IOfflineInstructService extends IService<ImOfflineInstruct> {

  void send(Integer code, Long recvUid, MsgStatusDTO data);

  List<ImOfflineInstruct> offlineList(Long userId, Long lastMsgId);
}
