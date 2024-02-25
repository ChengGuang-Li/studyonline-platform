package org.studyonline.captcha.service.impl;

import org.springframework.stereotype.Component;
import org.studyonline.captcha.service.CaptchaService;

import java.util.UUID;

/**
 * @Description: uuid generator
 * @Author: Chengguang Li
 * @Date: 25/02/2024 3:00 pm
 */
@Component("UUIDKeyGenerator")
public class UUIDKeyGenerator implements CaptchaService.KeyGenerator {
    @Override
    public String generate(String prefix) {
        String uuid = UUID.randomUUID().toString();
        return prefix + uuid.replaceAll("-", "");
    }
}

