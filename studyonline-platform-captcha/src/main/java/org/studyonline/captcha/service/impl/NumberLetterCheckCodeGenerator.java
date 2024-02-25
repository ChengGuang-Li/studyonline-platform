package org.studyonline.captcha.service.impl;

import org.springframework.stereotype.Component;
import org.studyonline.captcha.service.CaptchaService;

import java.util.Random;

/**
 * @Description: Number letter generator
 * @Author: Chengguang Li
 * @Date: 25/02/2024 3:02 pm
 */
@Component("NumberLetterCaptchaGenerator")
public class NumberLetterCheckCodeGenerator implements CaptchaService.CheckCodeGenerator {


    @Override
    public String generate(int length) {
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}