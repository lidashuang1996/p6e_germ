package com.sc.cache.context.controller;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.sc.cache.cache.ScCacheRedisAuth;
import com.sc.cache.context.controller.support.ScBaseController;
import com.sc.cache.model.ScCacheModel;
import com.sc.cache.model.ScResultModel;
import com.sc.cache.service.ScCacheService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/cache")
public class ScCacheController extends ScBaseController {

    @Resource
    private ScCacheService cacheService;


    private final ScCacheRedisAuth cacheAuth = new ScCacheRedisAuth();

    @RequestMapping("/get")
    public ScResultModel get(ScCacheModel.VoParam param) {
        try {
            if (param == null
                    || param.getType() == null
                    || param.getToken() == null) {
                return ScResultModel.build(ScResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final String account = cacheAuth.get(param.getToken());
                if (account == null) {
                    return ScResultModel.build(ScResultModel.Error.PARAMETER_EXCEPTION);
                } else {
                    final ScCacheModel.DtoResult result = cacheService.get(P6eCopyUtil.run(param, ScCacheModel.DtoParam.class).setAccount(account));
                    return ScResultModel.build(P6eCopyUtil.run(result, ScCacheModel.VoResult.class).setMap(result.getMap()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ScResultModel.build(ScResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    private static final String V = "lds1996";

    @RequestMapping("/set")
    public ScResultModel set(String v, @RequestBody ScCacheModel.VoParam param) {
        System.out.println(param);
        System.out.println(v);
        try {
            if (param == null
                    || param.getMap() == null
                    || param.getType() == null
                    || param.getAccount() == null) {
                return ScResultModel.build(ScResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                if (!V.equals(v)) {
                    return ScResultModel.build(ScResultModel.Error.PARAMETER_EXCEPTION);
                } else {
                    final ScCacheModel.DtoResult result = cacheService.set(P6eCopyUtil.run(param, ScCacheModel.DtoParam.class).setMap(param.getMap()));
                    return ScResultModel.build(P6eCopyUtil.run(result, ScCacheModel.VoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ScResultModel.build(ScResultModel.Error.SERVICE_EXCEPTION);
        }
    }

}
