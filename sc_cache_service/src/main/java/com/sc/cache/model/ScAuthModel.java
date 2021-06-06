package com.sc.cache.model;

import lombok.Data;
import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class ScAuthModel implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String account;
        private String password;
    }

    @Data
    public static class VoResult implements Serializable {
        private String token;
    }

    @Data
    public static class DtoParam implements Serializable {
        private String account;
        private String password;
    }

    @Data
    public static class DtoResult implements Serializable {
        private String token;
    }
}
