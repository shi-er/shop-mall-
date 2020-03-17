package com.li.yun.biao.common.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis cache 工具类
 */
@Component
public final class RedisUtil {
    private static RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean set(final String key, Object value, Date expire) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expireAt(key, expire);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean increment(final String key, double value, Date expireAt) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.increment(key, value);
            if (expireAt != null) {
                redisTemplate.expireAt(key, expireAt);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @param expireAt
     * @return
     */
    public Double inc(final String key, double value, Date expireAt) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        Double result = operations.increment(key, value);
        if (expireAt != null) {
            redisTemplate.expireAt(key, expireAt);
        }
        return result;
    }

    public Set keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 获取key的过期时间
     *
     * @param key 过期时间
     * @return 过期时间的秒数
     */
    public long expire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断一个key是否过期
     *
     * @param key key
     * @return boolean
     */
    public Boolean isExpire(String key) {
        Boolean isExpire = Boolean.FALSE;
        if (redisTemplate.getExpire(key, TimeUnit.SECONDS) <= 0)
            isExpire = Boolean.TRUE;
        return isExpire;
    }

    /**
     * setter方法注入RedisTemplate
     *
     * @param redisTemplate 对象
     */
    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }
}
