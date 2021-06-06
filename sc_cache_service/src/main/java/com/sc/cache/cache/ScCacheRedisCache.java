package com.sc.cache.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public class ScCacheRedisCache extends ScCacheRedis implements IScCacheCache {

    private static final String SOURCE_NAME = "A";

    @Override
    public void set(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(AUTH_NAME + key, value);
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
