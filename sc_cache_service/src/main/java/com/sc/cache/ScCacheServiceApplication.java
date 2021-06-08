package com.sc.cache;

import com.p6e.germ.common.utils.P6eSpringUtil;
import com.sc.cache.cache.ScCacheRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 入口文件
 * @author lidashuang
 */
@ServletComponentScan
@SpringBootApplication
public class ScCacheServiceApplication {

    public static void main(String[] args) {
        P6eSpringUtil.init(
                SpringApplication.run(ScCacheServiceApplication.class, args)
        );
        ScCacheRedis.init();
    }

}

