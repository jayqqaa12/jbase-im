package com.jayqqaa12.im.business.support;

import com.jayqqaa12.im.common.model.dto.RpcDTO;

/**
 * @author: 12
 * @create: 2019-12-27 14:32
 **/
@FunctionalInterface
public interface IHandler<T> {

  Object handle(RpcDTO req, T data);


}
