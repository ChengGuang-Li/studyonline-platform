package org.studyonline.captcha.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.captcha.model.CaptchaResultDto;
import org.studyonline.captcha.model.CaptchaParamsDto;
import org.studyonline.captcha.service.CaptchaService;
import org.studyonline.captcha.service.SendCodeService;

import javax.annotation.Resource;

/**
 * @Description: Verification code service interface
 * @Author: Chengguang Li
 * @Date: 25/02/2024 2:02 pm
 */

@Api(value = "Verification code service interface")
@RestController
public class CaptchaController {

    @Resource(name = "PicCaptchaService")
    private CaptchaService picCaptchaService;

   @Autowired
    SendCodeService sendCodeService;

    @ApiOperation(value="Generate verification information", notes="Generate verification information")
    @PostMapping(value = "/pic")
    public CaptchaResultDto generatePicCheckCode(CaptchaParamsDto captchaParamsDto){
        return picCaptchaService.generate(captchaParamsDto);
    }

    @ApiOperation(value="check", notes="check")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "businessName", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "key", value = "VerifyKey", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "code", value = "VerificationCode", required = true, dataType = "String", paramType="query")
    })
    @PostMapping(value = "/verify")
    public Boolean verify(String key, String code){
        Boolean isSuccess = picCaptchaService.verify(key,code);
        return isSuccess;
    }

    @PostMapping(value = "/email")
     public void sendEmail(@RequestParam("email") String email){
         sendCodeService.sendEMail(email);
     }

}
