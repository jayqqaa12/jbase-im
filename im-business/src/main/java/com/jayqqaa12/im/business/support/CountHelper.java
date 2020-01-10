package com.jayqqaa12.im.business.support;

import com.jayqqaa12.im.common.model.consts.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 主要实现计数的持久化
 * <p>
 * todo 修改为原子性更新总数+会话数
 *
 * @author: 12
 * @create: 2019-09-17 17:34
 **/
@Component
public class CountHelper {

  @Autowired
  RedisTemplate redisTemplate;


  private void increment(Long uid, Long sessionId, int value) {
    redisTemplate.opsForHash().increment(CacheConstants.REDIS_MSG_COUNT + uid, sessionId, value);
    redisTemplate.opsForHash().increment(CacheConstants.REDIS_MSG_COUNT_TOTAL, uid, value);

  }

  private void decrement(Long uid, Long sessionId, int value) {
    redisTemplate.opsForHash().increment(CacheConstants.REDIS_MSG_COUNT + uid, sessionId, -value);
    redisTemplate.opsForHash().increment(CacheConstants.REDIS_MSG_COUNT_TOTAL, uid, -value);

  }


  /**
   * 清空
   *
   * @param uid
   * @param sessionId
   */
  public void reset(Long uid, Long sessionId) {

    Integer c = get(uid, sessionId);

    redisTemplate.opsForHash().delete(CacheConstants.REDIS_MSG_COUNT + uid, sessionId);

    redisTemplate.opsForHash().increment(CacheConstants.REDIS_MSG_COUNT_TOTAL, uid, -c);
  }

  /**
   * +1
   *
   * @param uid
   * @param sessionId
   */
  public void increment(Long uid, Long sessionId) {
    increment(uid, sessionId, 1);
  }

  /**
   * -1
   *
   * @param uid
   * @param sessionId
   */
  public void decrement(Long uid, Long sessionId) {
    decrement(uid, sessionId, 1);
  }


  public Integer get(Long uid, Long sessionId) {
    return (Integer) redisTemplate.opsForHash().get(CacheConstants.REDIS_MSG_COUNT + uid, sessionId);
  }


}
