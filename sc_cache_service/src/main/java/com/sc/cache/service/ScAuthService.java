package com.sc.cache.service;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.sc.cache.cache.ScCacheRedisAuth;
import com.sc.cache.model.ScAuthModel;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class ScAuthService {

    private final ScCacheRedisAuth cacheAuth = new ScCacheRedisAuth();

    public ScAuthModel.DtoResult auth(ScAuthModel.DtoParam param) {
        final ScAuthModel.DtoResult result = new ScAuthModel.DtoResult();
        final boolean b = verification(param.getAccount(), param.getPassword());
        if (b) {
            final String uuid = P6eGeneratorUtil.uuid();
            cacheAuth.set(uuid, param.getAccount());
            result.setToken(uuid);
        } else {
            result.setToken("");
        }
        return result;
    }

    private synchronized static boolean verification(String account, String password) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream("./ap.txt");
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                final String [] ls = line.trim().split(" ");
                final boolean r = ls[0].trim().equals(account) && ls[1].trim().equals(password);
                if (r) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
