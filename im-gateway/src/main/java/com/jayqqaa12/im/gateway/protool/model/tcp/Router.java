package com.jayqqaa12.im.gateway.protool.model.tcp;

import com.jayqqaa12.im.gateway.protool.model.vo.TcpReqVO;

@FunctionalInterface
public interface Router<T> {


    void handle(TcpContext context, TcpReqVO req, T data) throws Exception;

}
