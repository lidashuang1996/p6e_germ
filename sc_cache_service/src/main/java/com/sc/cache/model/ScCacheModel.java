package com.sc.cache.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class ScCacheModel implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String token;
        private String account;
        private String type;
        private Map<String, Object> map;
    }

    @Data
    @Accessors(chain = true)
    public static class VoResult implements Serializable {
        private Map<String, Object> map;
    }

    @Data
    @Accessors(chain = true)
    public static class DtoParam implements Serializable {
        private String account;
        private String type;
        private Map<String, Object> map;
    }

    @Data
    public static class DtoResult implements Serializable {
        private Map<String, Object> map;
    }
}
