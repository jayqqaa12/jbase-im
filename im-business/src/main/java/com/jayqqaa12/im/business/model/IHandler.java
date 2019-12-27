package com.jayqqaa12.im.business.model;

/**
 * @author: 12
 * @create: 2019-12-27 14:32
 **/
@FunctionalInterface
public interface IHandler<T> {

  Object handle(T data);


}
