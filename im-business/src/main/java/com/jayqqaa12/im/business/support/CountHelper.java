package com.jayqqaa12.im.business.support;

import com.jayqqaa12.im.common.model.consts.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 主要实现计数的持久化
 *
 * todo 修改为原子性更新总数+会话数
 *
 * @author: 12
 * @create: 2019-09-17 17:34
 **/
@Component
public class CountHelper {

    @Autowired
    RedisTemplate redisTemplate;


    public void increment(String uid, String sessionId, int value) {


        redisTemplate.opsForHash().increment(CacheConstants.REDIS_TCP_COUNT + uid, sessionId, value);
    }


    /**
     * 清空
     * @param uid
     * @param sessionId
     */
    public void reset(String uid, String sessionId) {
        redisTemplate.opsForHash().delete(CacheConstants.REDIS_TCP_COUNT + uid, sessionId);

    }


    public void decrement(String uid, String sessionId, int value) {

        redisTemplate.opsForHash().increment(CacheConstants.REDIS_TCP_COUNT + uid, sessionId, -value);
    }


    /**
     * +1
     * @param uid
     * @param sessionId
     */
    public void increment(String uid, String sessionId) {
        increment(uid, sessionId, 1);
    }

    /**
     * -1
     * @param uid
     * @param sessionId
     */
    public void decrement(String uid, String sessionId) {
        decrement(uid, sessionId, 1);
    }


    public Integer get(String uid, String sessionId) {
     return (Integer) redisTemplate.opsForHash().get(CacheConstants.REDIS_TCP_COUNT + uid, sessionId);
    }

 
 
}
