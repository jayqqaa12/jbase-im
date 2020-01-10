package com.jayqqaa12.im.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jayqqaa12.im.business.model.dto.MsgListDTO;
import com.jayqqaa12.im.business.model.entity.ImMsg;

import java.util.List;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
public interface IMsgService extends IService<ImMsg> {

    void saveMsg(ImMsg msg);

    void deleteMsg(Long msgId, Long uid);

  List<ImMsg> historyList(Long userId, MsgListDTO data);

}
