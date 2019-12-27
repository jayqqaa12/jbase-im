package com.jayqqaa12.im.gateway.support;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Maps;
import com.jayqqaa12.im.common.util.NodeKit;
import com.jayqqaa12.im.gateway.protool.base.RespChannel;
import com.jayqqaa12.im.common.model.vo.RetryVo;
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
  DelayQueue<TcpRespVO> delayQueue = new DelayQueue<>();
  Map<Long, TcpRespVO> msgMap = Maps.newConcurrentMap();

  ExecutorService executors = Executors.newSingleThreadExecutor("sendMsg-delay");
  volatile boolean close;
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
  public void send(TcpRespVO response, boolean forward) {

    //标示一下当前节点
    response.setNodeId(NodeKit.getNodeId());

    // 发送

    List<RespChannel> list = regHelper.getRespChannelByUserOrDevice(response.getDest());

    for (RespChannel respChannel : list) {
      respChannel.resp(response);
    }

    if(list.isEmpty()) removeRetryMsg(response.getRespId());

    //如果可能是多平台登录 都要判断一下是否在其他节点有登录进行转发
    if (forward) {
      forwardHelper.forward(response.getDest(), response);
    }

  }


  public void sendAndRetryForLocal(TcpRespVO response) {
    sendAndRetry(response, false);
  }


  public void sendAndRetry(TcpRespVO response) {

    sendAndRetry(response, true);
  }

  /**
   * 带重发的发送
   *
   * @param response
   */
  public void sendAndRetry(TcpRespVO response, boolean forward) {

    Assert.notNull(response.getRespId(), "msg id can't null");

    if (response.getRetry() == null) {
      response.setRetry(new RetryVo());
    }

    if (response.getRetry().getRetryTimes() > response.getRetry().getMaxRetryTimes()) {
      removeRetryMsg(response.getRespId());

      log.error("目标{} 重发失败 msgid={}", response.getDest(), response.getRespId());
      return;
    }
    boolean localStatus = regHelper.getLocalStatus(response.getDest());

    //如果本地不在线的话
    if (!localStatus) {
      boolean status = false;
      //如果是转发的消息 本地不在线就说明不在线
      if (!forward) status = regHelper.getOnlineStatus(response.getDest());

      if (!status) {
        removeRetryMsg(response.getRespId());
        log.info("目标 {}不在线 存储为离线消息 {}", response.getDest(), response.getRespId());
      }
      return;
    }

    //过滤重复的
    if (delayQueue.contains(response)) return;

    // 加入重发队列
    response.setTimestamp(getNextTime(response.getRetry().getRetryTimes()));

    msgMap.put(response.getRespId(), response);
    delayQueue.add(response);

    //  修改消息重试次数
    response.getRetry().setRetryTimes(response.getRetry().getRetryTimes() + 1);

    send(response, forward);

  }


  /**
   * 移除重发的消息
   *
   * @param respId
   */
  public void removeRetryMsg(Long respId) {

    Optional.ofNullable(msgMap.remove(respId)).ifPresent((req) -> {
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
