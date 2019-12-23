package com.jayqqaa12.im.gateway.support;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Maps;
import com.jayqqaa12.im.common.model.tcp.RespChannel;
import com.jayqqaa12.im.common.model.dto.RetryDTO;
import com.jayqqaa12.im.common.model.vo.TcpRespVO;
import com.jayqqaa12.jbase.util.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.ExponentialBackOff;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;

/**
 * @author: 12
 * @create: 2019-09-11 15:54
 **/
@Component
@Slf4j(topic = "SEND_MSG")
public class SendHelper {

    /**
     * 无界队列 注意容量
     */
    private DelayQueue<TcpRespVO> delayQueue = new DelayQueue<>();
    private Map<Long, TcpRespVO> msgMap = Maps.newConcurrentMap();

    private ExecutorService executors = Executors.newSingleThreadExecutor("sendMsg-delay");
    private volatile boolean close;

    @Autowired
    RegHelper regHelper;
    @Autowired
    ForwardHelper forwardHelper;



    @PostConstruct
    public void init() {
        log.info("start delay task ");

        //sendMsg task
        executors.execute(() -> {
            while (!close) {
                try {
//                    DisruptorKit.onEvent(TcpConstants.DISRUPTOR_RETRY, delayQueue.take());
                     sendAndRetry((delayQueue.take()));
                } catch (Exception e) {
                    log.info("sendMsg delay task error {}", e);
                }
            }
            log.info("stop delay task");
        });


    }


    @PreDestroy
    public void close() {
        close = true;
        executors.shutdown();
    }


    /**
     * 直接发送
     *
     * @param response
     */
    public void send(TcpRespVO response) {
        // 发送

        List<RespChannel> list = regHelper.getRespChannelByUserOrDevice(response.getDest());

        for (RespChannel respChannel : list) {
            respChannel.resp(response);
        }

        //forward
        if (list.isEmpty()) {
            //从当前重发队列移除
            removeRetryMsg(response.getMsgId());
            forwardHelper.forward(regHelper.getOnlineDest(response.getDest()), response);
        }

    }


    /**
     * 带重发的发送
     *
     * @param response
     */
    public void sendAndRetry(TcpRespVO response) {

        Assert.notNull(response.getMsgId(), "msg id can't null");

        if (response.getRetry() == null) {
            response.setRetry(new RetryDTO());
        }

        if (response.getRetry().getRetryTimes() > response.getRetry().getMaxRetryTimes()) {
            removeRetryMsg(response.getMsgId());

            log.error("目标{} 重发失败 msgid={}",response.getDest(),response.getMsgId());
            return;
        }
        if (!regHelper.getOnlineStatus(response.getDest())) {
            removeRetryMsg(response.getMsgId());

            log.info("目标 {}不在线 存储为离线消息 {}",response.getDest(),response.getMsgId());
            return;
        }

        //过滤重复的
        if (delayQueue.contains(response)) return;

        // 加入重发队列
        response.setTimestamp(getNextTime(response.getRetry().getRetryTimes()));

        msgMap.put(response.getMsgId(), response);
        delayQueue.add(response);


        //  修改消息重试次数
        response.getRetry().setRetryTimes(response.getRetry().getRetryTimes() + 1);

        send(response);


    }


    /**
     * 移除重发的消息
     *
     * @param msgId
     */
    public void removeRetryMsg(Long msgId) {

        Optional.ofNullable(msgMap.remove(msgId)).ifPresent((req) -> {
            delayQueue.remove(req);
        });
    }


    private long getNextTime(int i) {
        long initialInterval = 2000;//初始间隔
        long maxInterval = 60 * 60 * 1000L;//最大间隔
        long maxElapsedTime = 60 * 60 * 1000L;//最大时间间隔
        double multiplier = 2;//递增倍数（即下次间隔是上次的多少倍）
        ExponentialBackOff backOff = new ExponentialBackOff(initialInterval, multiplier);
        backOff.setMaxInterval(maxInterval);
        backOff.setMaxElapsedTime(maxElapsedTime);
        BackOffExecution execution = backOff.start();

        for (int j = 0; j < i; j++) {
            execution.nextBackOff();
        }

        return execution.nextBackOff() + System.currentTimeMillis();
    }


}
