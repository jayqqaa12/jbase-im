package com.jayqqaa12.im.gateway.support;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.jayqqaa12.im.gateway.protool.model.tcp.RespChannel;
import com.jayqqaa12.im.common.model.consts.CacheConstants;
import com.jayqqaa12.im.gateway.protool.model.dto.RegInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@Slf4j
public class RegHelper {

    private Multimap<String, ServerEntry> deviceMap = LinkedListMultimap.create();
    private Multimap<Long, ServerEntry> userIdsMap = LinkedListMultimap.create();

    private ConcurrentHashMap<RespChannel, ServerEntry> channelMapUserCache = new ConcurrentHashMap<>();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    IpHepler ipHepler;


    public synchronized void register(RegInfoDTO info, RespChannel handler) {

        check(info);

        ServerEntry entry = new ServerEntry(info, handler);

        if (info.getDevice() != null) {
            deviceMap.put(info.getDevice(), entry);
        }
        if (info.getUserId() != null) {
            userIdsMap.put(info.getUserId(), entry);
        }

        channelMapUserCache.put(handler, entry);

        // reg to redis  绑定当前channel 和节点ip
        redisTemplate.opsForValue().set(CacheConstants.REDIS_TCP_SESSION + info.getUserOrDevice(), ipHepler.getNodeIp(),1,TimeUnit.DAYS);

    }



    public List<RespChannel> getRespChannelByUserOrDevice(String key) {

        List<RespChannel> list = getRespChannel(key);
        try {
            if (list.isEmpty()) return getRespChannel(Long.parseLong(key));
        } catch (Exception e) {
        }

        return list;

    }

    public boolean getOnlineStatus(String dest) {

        boolean status = getRespChannelByUserOrDevice(dest).isEmpty();

        // 检查redis 在线状态
        if (status) status = redisTemplate.opsForValue().get(CacheConstants.REDIS_TCP_SESSION + dest) == null;

        return !status;
    }


    public List<RespChannel> getRespChannel(Long uid) {

        Collection<ServerEntry> result = userIdsMap.get(uid);

        if (result != null && result.size() > 0) {
            return result.stream().map(v -> v.responseHandler).collect(Collectors.toList());
        }

        return Collections.EMPTY_LIST;
    }

    public List<RespChannel> getRespChannel(String device) {
        Collection<ServerEntry> result = deviceMap.get(device);

        if (result != null && result.size() > 0) {
            return result.stream().map(v -> v.responseHandler).collect(Collectors.toList());
        }

        return Collections.EMPTY_LIST;

    }


    public Collection<RespChannel> getAllResponseHandler() {
        return deviceMap.values().stream()
                .map(entry -> entry.responseHandler)
                .collect(Collectors.toList());
    }


    public synchronized void unregister(RespChannel handler) {
        ServerEntry entry = channelMapUserCache.remove(handler);
        if (entry != null) {
            deviceMap.remove(entry.info.getDevice(), entry);
            if (entry.info.isLogin()) {
                userIdsMap.remove(entry.info.getUserId(), entry);
            }

            redisTemplate.delete(CacheConstants.REDIS_TCP_SESSION + entry.info.getUserOrDevice());


        }
    }

    public RegInfoDTO getRegisterInfo(RespChannel respHandler) {

        ServerEntry entry = channelMapUserCache.get(respHandler);

        if (entry != null) {
            return entry.info;
        }

        return null;
    }


    private void check(RegInfoDTO info) {

        if (info.isLogin()) {
            List<RespChannel> handlerList = getRespChannel(info.getUserId());

            if (handlerList.size()>2) {
                handlerList.remove(0).close();
                log.info("user {} register close before channel ", info.getUserId());
            }

        } else {
            List<RespChannel> handlerList = getRespChannel(info.getDevice());

            if (!handlerList.isEmpty()) {
                handlerList.remove(0).close();
                log.info("user {} register close before channel ", info.getDevice());
            }
        }

    }

    public String getOnlineDest(String dest) {

        return (String) redisTemplate.opsForValue().get(CacheConstants.REDIS_TCP_SESSION + dest);
    }

    static class ServerEntry {
        RegInfoDTO info;
        RespChannel responseHandler;

        ServerEntry(RegInfoDTO info, RespChannel responseHandler) {
            this.info = info;
            this.responseHandler = responseHandler;
        }
    }
}
