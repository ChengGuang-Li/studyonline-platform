package org.studyonline.captcha.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.studyonline.captcha.model.CaptchaParamsDto;
import org.studyonline.captcha.model.CaptchaResultDto;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 25/02/2024 2:59 pm
 */
@Slf4j
public abstract class AbstractCaptchaService implements CaptchaService {

    protected CheckCodeGenerator checkCodeGenerator;
    protected KeyGenerator keyGenerator;
    protected CheckCodeStore checkCodeStore;

    public abstract void  setCheckCodeGenerator(CheckCodeGenerator checkCodeGenerator);
    public abstract void  setKeyGenerator(KeyGenerator keyGenerator);
    public abstract void  setCheckCodeStore(CheckCodeStore CheckCodeStore);


    /**
     * @description Generate validation public methods
     * @param captchaParamsDto Generate verification code parameters
     * @param code_length Verification code length
     * @param keyPrefix prefix of key
     * @param expire expire time
     * @return org.studyonline.captcha.service.AbstractCaptchaService.GenerateResult  result
     * @Author: Chengguang Li
     * @Date: 25/02/2024 2:59 pm
     */
    public GenerateResult generate(CaptchaParamsDto captchaParamsDto, Integer code_length, String keyPrefix, Integer expire){
        //Generate four-digit verification code
        String code = checkCodeGenerator.generate(code_length);
        log.debug("Generate verification code:{}",code);
        //Generate a key
        String key = keyGenerator.generate(keyPrefix);

        //Store verification code
        checkCodeStore.set(key,code,expire);
        //Return verification code generation results
        GenerateResult generateResult = new GenerateResult();
        generateResult.setKey(key);
        generateResult.setCode(code);
        return generateResult;
    }

    @Data
    protected class GenerateResult{
        String key;
        String code;
    }


    public abstract CaptchaResultDto generate(CaptchaParamsDto captchaParamsDto);


    public boolean verify(String key, String code){
        if (StringUtils.isBlank(key) || StringUtils.isBlank(code)){
            return false;
        }
        String code_l = checkCodeStore.get(key);
        if (code_l == null){
            return false;
        }
        boolean result = code_l.equalsIgnoreCase(code);
        if(result){
            //Delete verification code
            checkCodeStore.remove(key);
        }
        return result;
    }


}

