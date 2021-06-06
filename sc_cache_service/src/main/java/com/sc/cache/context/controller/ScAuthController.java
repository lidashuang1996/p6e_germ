package com.sc.cache.context.controller;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.sc.cache.context.controller.support.ScBaseController;
import com.sc.cache.model.ScAuthModel;
import com.sc.cache.model.ScResultModel;
import com.sc.cache.service.ScAuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
public class ScAuthController extends ScBaseController {

    @Resource
    private ScAuthService authService;

    @RequestMapping
    public ScResultModel def(ScAuthModel.VoParam param) {
        try {
            if (param == null
                    || param.getAccount() == null
                    || param.getPassword() == null) {
                return ScResultModel.build(ScResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final ScAuthModel.DtoResult result =
                        authService.auth(P6eCopyUtil.run(param, ScAuthModel.DtoParam.class));
                return ScResultModel.build(P6eCopyUtil.run(result, ScAuthModel.VoResult.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ScResultModel.build(ScResultModel.Error.SERVICE_EXCEPTION);
        }
    }

}
