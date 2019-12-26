package com.jayqqaa12.im.gateway.protool.model.tcp;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jayqqaa12.im.common.model.consts.Resp;
import com.jayqqaa12.im.gateway.protool.model.vo.TcpReqVO;
import com.jayqqaa12.im.gateway.protool.model.vo.TcpRespVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Data
@Slf4j
public class TcpContext implements Cloneable {

    private boolean login;
    private Long connectTime;

    private RespChannel respChannel;


    private Integer userId;

    private String device;

    private static Cache<String, TcpRespVO> cache = CacheBuilder.newBuilder()
            .maximumSize(1024 * 1024 * 100)
            .expireAfterAccess(10, TimeUnit.MINUTES).build();


    public String getUserOrDevice() {

        Integer id = getUserId();
        return id != null ? id + "" : getDevice();
    }


    public void response(TcpReqVO req, Integer code) {
        TcpRespVO vo = TcpRespVO.response(req, code, null);
        respChannel.resp(vo);
        cache.put(req.getUuid(), vo);
    }

    public void response(TcpReqVO req, Object data) {
        TcpRespVO vo = TcpRespVO.response(req, Resp.OK, data);
        respChannel.resp(vo);
        cache.put(req.getUuid(), vo);
    }

    public void response(TcpReqVO req, Integer code, Object data) {
        TcpRespVO vo = TcpRespVO.response(req, code, data);
        respChannel.resp(vo);
        cache.put(req.getUuid(), vo);
    }


    public void responseError(TcpReqVO req, Integer code, String msg) {
        responseError(req,code, msg, null);
    }

    public void responseError(TcpReqVO req, Integer code, String msg, Object data) {
        TcpRespVO vo = TcpRespVO.response(req, code, msg, data);
        respChannel.resp(vo);
        cache.put(req.getUuid(), vo);
    }

    public void error(  String msg) {
        error( Resp.ERROR, msg);
    }


    public void error(  Integer code, String msg) {
        TcpRespVO vo = TcpRespVO.response(code, msg, null, null, null);
        respChannel.resp(vo);
    }


    public boolean isRepectResp(TcpReqVO req) {

        TcpRespVO respVO = cache.getIfPresent(req.getUuid());
        //以前已经有响应的直接返回
        if (respVO != null) respChannel.resp(respVO);

        return respVO != null;
    }
}
