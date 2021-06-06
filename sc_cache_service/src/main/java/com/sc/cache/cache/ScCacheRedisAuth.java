package com.sc.cache.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
public class ScCacheRedisAuth extends ScCacheRedis implements IScCacheAuth {

    private static final String SOURCE_NAME = "A";

    @Override
    public void set(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(AUTH_NAME + key, value, AUTH_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(AUTH_NAME + key);
    }

    @Override
    public void del(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(AUTH_NAME + key);
    }

}
