package org.studyonline.captcha.model;

import lombok.Data;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 25/02/2024 2:05 pm
 */

@Data
public class CaptchaResultDto {

    /**
     * key is used for verification
     */
    private String key;

    /**
     * Obfuscated content
     * Example:
     * 1. The image verification code is: image base64 encoding
     * 2. SMS verification code is: null
     * 3. The email verification code is: null
     * 4. Email link click verification is: null
     * ...
     */
    private String aliasing;
}
