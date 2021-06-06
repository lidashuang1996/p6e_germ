package com.sc.cache.service;

import com.p6e.germ.common.utils.P6eJsonUtil;
import com.sc.cache.cache.ScCacheRedisCache;
import com.sc.cache.model.ScCacheModel;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class ScCacheService {

    private final ScCacheRedisCache cacheAuth = new ScCacheRedisCache();

    public ScCacheModel.DtoResult get(ScCacheModel.DtoParam param) {
        final ScCacheModel.DtoResult result = new ScCacheModel.DtoResult();
        final String content = cacheAuth.get(param.getType() + "_" + param.getAccount());
        if (content != null) {
            final Map<String, Object> map = P6eJsonUtil.fromJsonToMap(content, String.class, Object.class);
            result.setMap(map);
        }
        return result;
    }

    public ScCacheModel.DtoResult set(ScCacheModel.DtoParam param) {
        final ScCacheModel.DtoResult result = new ScCacheModel.DtoResult();
        cacheAuth.set(param.getType() + "_" + param.getAccount(), P6eJsonUtil.toJson(param.getMap()));
        return result;
    }
}
