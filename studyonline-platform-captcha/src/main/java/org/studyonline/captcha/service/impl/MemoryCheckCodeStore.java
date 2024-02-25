package org.studyonline.captcha.service.impl;

import org.springframework.stereotype.Component;
import org.studyonline.captcha.service.CaptchaService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Use local memory to store verification codes for testing
 * @Author: Chengguang Li
 * @Date: 25/02/2024 3:02 pm
 */
@Component("MemoryCaptchaStore")
public class MemoryCheckCodeStore implements CaptchaService.CheckCodeStore {

    Map<String,String> map = new HashMap<String,String>();

    @Override
    public void set(String key, String value, Integer expire) {
        map.put(key,value);
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }
}


