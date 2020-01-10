package com.jayqqaa12.im.business.support;

import com.jayqqaa12.im.common.model.ReqContent;

/**
 * @author: 12
 * @create: 2019-12-27 14:32
 **/
@FunctionalInterface
public interface IHandler<T> {


  /**
   * 实现这个方法 配合@Handler注解 就能处理对应的指令
   *
   * @param req 请求的上下文
   * @param data 请求数据
   * @return 返回数据 如果null 就是不返回数据
   */
  Object handle(ReqContent req, T data);


}
