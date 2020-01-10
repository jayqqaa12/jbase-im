package com.jayqqaa12.im.common.rpc;

import com.jayqqaa12.im.common.model.ReqContent;

/**
 *
 * 业务分发接口
 *
 * 业务服务实现 对指令进行分发处理
 *
 *
 * @author: 12
 * @create: 2020-01-10 14:46
 **/
public interface BusinessDispatcher  {

  public Object handler(ReqContent req) throws Exception;
}
