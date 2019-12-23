package com.jayqqaa12.im.business.service.impl;

import com.jayqqaa12.im.common.model.entity.ImOfflineInstruct;
import com.jayqqaa12.im.business.mapper.ImOfflineInstructMapper;
import com.jayqqaa12.im.business.service.IOfflineInstructService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 
记录离线的指令 （指令可能是删除消息，撤回消息之类的） 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-12-23
 */
@Service
public class ImOfflineInstructServiceImpl extends ServiceImpl<ImOfflineInstructMapper, ImOfflineInstruct> implements IOfflineInstructService {

}
