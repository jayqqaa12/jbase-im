package com.jayqqaa12.im.common.model.vo;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jayqqaa12.im.common.model.consts.Resp;
import com.jayqqaa12.im.common.model.dto.RetryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TcpRespVO<T> implements Delayed {

    protected String uuid;   // 请求的唯一标示
    protected Integer code;
    protected Integer reqCode;
    protected String message;
    protected Long timestamp;
    protected T data;

    //唯一标识 用来防止重发的重复
    private Long msgId;

    private String dest;


    private transient RetryDTO retry;


    public static TcpRespVO heart() {
        return TcpRespVO.builder().code(Resp.HEART).build();
    }


    public static TcpRespVO<Object> response(Integer code, Object data, String dest,Long msgId ) {
        return response(code, null, data, dest, msgId );
    }


    public static TcpRespVO<Object> response(Integer code, Object data, String dest ) {
        return response(code, null, data, dest, IdWorker.getId());
    }

    public static TcpRespVO<Object> response(Integer code, String msg, Object data, String dest, Long msgId) {
        return TcpRespVO.builder()
                .code(code)
                .msgId(msgId)
                .message(msg)
                .timestamp(System.currentTimeMillis())
                .data(data)
                .dest(dest)
                .build();

    }

    public static TcpRespVO<Object> response(TcpReqVO request, Integer code, Object data) {
        return response(request, code, null, data);
    }

    public static TcpRespVO<Object> response(TcpReqVO request, Integer code, String msg, Object data) {

        return TcpRespVO.builder()
                .uuid(request.getUuid())
                .code(code)
                .reqCode(request.getCode())
                .message(msg)
                .timestamp(System.currentTimeMillis())
                .data(data)
                .build();

    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(timestamp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }


    @Override
    public int compareTo(Delayed o) {

        TcpRespVO resp = (TcpRespVO) o;
        return this.getTimestamp().compareTo(resp.getTimestamp());
    }


}
