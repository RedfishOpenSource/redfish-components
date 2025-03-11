package com.redfish.components.common.lock;

import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zly
 * @date 2025/2/28
 * @description TODO
 * @Copyright: 儒松科技
 */
@Component
public class RedisDistributedLock {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;


    /**
     * 尝试获取分布式锁
     * @param lockKey 锁的名称
     * @param requestId 请求标识符，确保解锁的安全性
     * @param expireTime 锁的有效期（秒）
     * @return 是否成功获取锁
     */
    public boolean tryLock(String lockKey, String requestId, long expireTime) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireTime, TimeUnit.SECONDS);
        return result != null && result;
    }

    /**
     * 释放分布式锁
     * @param lockKey 锁的名称
     * @param requestId 请求标识符
     * @return 是否成功释放锁
     */
    public boolean unlock(String lockKey, String requestId) {
        // Lua脚本保证原子性操作
        String luaScript =
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "   return redis.call('del', KEYS[1]) " +
                        "else " +
                        "   return 0 " +
                        "end";

        Long result = redisTemplate.execute(
                (RedisCallback<Long>) connection -> connection.eval(
                        luaScript.getBytes(),
                        ReturnType.INTEGER,
                        1,
                        lockKey.getBytes(),
                        requestId.getBytes()
                )
        );

        return result != null && result.equals(1L);
    }

}
