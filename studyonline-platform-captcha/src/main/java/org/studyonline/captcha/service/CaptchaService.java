package org.studyonline.captcha.service;

import org.studyonline.captcha.model.CaptchaParamsDto;
import org.studyonline.captcha.model.CaptchaResultDto;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 25/02/2024 2:58 pm
 */
public interface CaptchaService {


    /**
     * @description Generate verification code
     * @param captchaParamsDto Generate verification code parameters
     * @return  org.studyonline.captcha.model.CaptchaResultDto result
     * @Author: Chengguang Li
     * @Date: 25/02/2024 2:58 pm
     */
    CaptchaResultDto generate(CaptchaParamsDto captchaParamsDto);

    /**
     * @description Verify verification code
     * @param key
     * @param code
     * @return boolean
     * @Author: Chengguang Li
     * @Date: 25/02/2024 2:58 pm
     */
    public boolean verify(String key, String code);


    /**
     * @description verification code generator
     * @Author: Chengguang Li
     * @Date: 25/02/2024 2:58 pm
     */
    public interface CheckCodeGenerator{
        /**
         * verification code generator
         * @return verification code
         */
        String generate(int length);


    }

    /**
     * @description key generator
     * @Author: Chengguang Li
     * @Date: 25/02/2024 2:58 pm
     */
    public interface KeyGenerator{

        /**
         * key generator
         * @return verification code
         */
        String generate(String prefix);
    }


    /**
     * @description Verification code storage
     * @Author: Chengguang Li
     * @Date: 25/02/2024 2:58 pm
     */
    public interface CheckCodeStore{

        /**
         * @description Set key to cache
         * @param key key
         * @param value value
         * @param expire Expiration time, unit seconds
         * @return void
         * @Author: Chengguang Li
         * @Date: 25/02/2024 2:58 pm
         */
        void set(String key, String value, Integer expire);

        String get(String key);

        void remove(String key);
    }
}