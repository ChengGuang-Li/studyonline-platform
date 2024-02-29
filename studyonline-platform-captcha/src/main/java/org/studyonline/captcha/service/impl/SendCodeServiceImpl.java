package org.studyonline.captcha.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.captcha.service.CaptchaService;
import org.studyonline.captcha.service.SendCodeService;
import org.studyonline.captcha.utils.MailUtil;

import javax.mail.MessagingException;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 29/02/2024 9:56 pm
 */

@Service
@Slf4j
public class SendCodeServiceImpl implements SendCodeService {
    public final Long CODE_TTL = 120L;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    CaptchaService.CheckCodeGenerator  checkCodeGenerator;

    @Override
    public void sendEMail(String email) {
        String code = checkCodeGenerator.generate(4); //Verification Code
        try{
            MailUtil.sendTestMail(email, code);
        }catch (MessagingException e){
            log.error("Send Email Failed; code: {}, error message: {}",code,e.getMessage());
            StudyOnlineException.cast("Send Email Failed");
        }
        //save email verification code into redis, TTL 120s;
        redisTemplate.opsForValue().set(email, code, CODE_TTL, TimeUnit.SECONDS);
    }
}
