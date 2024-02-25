package org.studyonline.ucenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:28 am
 */
@FeignClient(value = "captcha",fallbackFactory = CaptchaClientFactory.class)
@RequestMapping("/captcha")
public interface CaptchaClient {

    @PostMapping(value = "/verify")
    public Boolean verify(@RequestParam("key")String key,@RequestParam("code") String code);
}
