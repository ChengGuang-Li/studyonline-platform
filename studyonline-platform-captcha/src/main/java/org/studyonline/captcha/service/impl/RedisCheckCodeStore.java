package org.studyonline.captcha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.studyonline.captcha.service.CaptchaService;

import java.util.concurrent.TimeUnit;

/**
 * @Description: Use redis to store verification codes for testing
 * @Author: Chengguang Li
 * @Date: 25/02/2024 3:01 pm
 */
@Component("RedisCaptchaStore")
public class RedisCheckCodeStore implements CaptchaService.CheckCodeStore {

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void set(String key, String value, Integer expire) {
        redisTemplate.opsForValue().set(key,value,expire, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}

