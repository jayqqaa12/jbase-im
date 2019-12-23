package com.jayqqaa12.im.common.model.tcp;

import com.jayqqaa12.im.common.model.vo.TcpReqVO;

@FunctionalInterface
public interface Router<T> {


    void handle(TcpContext context, TcpReqVO req, T data) throws Exception;

}
