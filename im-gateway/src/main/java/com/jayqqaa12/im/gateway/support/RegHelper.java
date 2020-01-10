package com.jayqqaa12.im.gateway.support;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.jayqqaa12.im.common.model.consts.CacheConstants;
import com.jayqqaa12.im.common.util.NodeKit;
import com.jayqqaa12.im.gateway.protool.base.RespChannel;
import com.jayqqaa12.im.gateway.protool.base.TcpContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class RegHelper {

  private Multimap<String, RespChannel> deviceMap = LinkedListMultimap.create();
  private Multimap<Long, RespChannel> userIdsMap = LinkedListMultimap.create();

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  SendHelper sendHelper;


  public synchronized void register(TcpContext context) {

    check(context);


    if (context.getDevice() != null) {
      deviceMap.put(context.getDevice(), context.getRespChannel());
    }
    if (context.getUserId() != null) {
      userIdsMap.put(context.getUserId(), context.getRespChannel());
    }


    // reg to redis  绑定当前channel 和节点ip
    redisTemplate.opsForHash().put(CacheConstants.REDIS_TCP_SESSION + context.getUserOrDevice(),
      context.getPlatform(), NodeKit.getNodeId());

  }


  public List<RespChannel> getRespChannelByUserOrDevice(String key) {

    List<RespChannel> list = getRespChannel(key);
    try {
      if (list.isEmpty()) return getRespChannel(Long.parseLong(key));
    } catch (Exception e) {
    }

    return list;

  }


  public List<RespChannel> getRespChannel(Long uid) {

    Collection<RespChannel> result = userIdsMap.get(uid);

    if (result != null && result.size() > 0) {
      return result.stream().collect(Collectors.toList());
    }

    return Collections.EMPTY_LIST;
  }

  public List<RespChannel> getRespChannel(String device) {
    Collection<RespChannel> result = deviceMap.get(device);

    if (result != null && result.size() > 0) {
      return result.stream().collect(Collectors.toList());
    }

    return Collections.EMPTY_LIST;

  }


  public Collection<RespChannel> getAllResponseHandler() {
    return deviceMap.values().stream()
      .collect(Collectors.toList());
  }


  public synchronized void unregister(TcpContext context) {

    deviceMap.remove(context.getDevice(), context.getRespChannel());
    if (context.isLogin()) {
      userIdsMap.remove(context.getUserId(), context.getRespChannel());
    }

    redisTemplate.opsForHash().delete(CacheConstants.REDIS_TCP_SESSION
      + context.getUserOrDevice(), context.getPlatform());

  }


  private void check(TcpContext context) {

    if (context.isLogin()) {
      List<RespChannel> handlerList = getRespChannel(context.getUserId());

      if (handlerList.size() > 2) {
        handlerList.remove(0).close();
        log.info("user {} register close before channel ", context.getUserId());
      }

    } else {
      List<RespChannel> handlerList = getRespChannel(context.getDevice());

      if (!handlerList.isEmpty()) {
        handlerList.remove(0).close();
        log.info("user {} register close before channel ", context.getDevice());
      }
    }

  }

  public List<String> getOnlineDest(String dest) {

    return redisTemplate.opsForHash().values(CacheConstants.REDIS_TCP_SESSION + dest);
  }


  public boolean getLocalStatus(String dest) {

    return !getRespChannelByUserOrDevice(dest).isEmpty();
  }

  public boolean getOnlineStatus(String dest) {
    // 检查redis 在线状态
    return !getOnlineDest(dest).isEmpty();
  }

}
