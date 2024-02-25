package org.studyonline.captcha.model;

import lombok.Data;

/**
 * @Description: Verification code generation parameter class
 * @Author: Chengguang Li
 * @Date: 25/02/2024 2:04 pm
 */

@Data
public class CaptchaParamsDto {
    /**
     * Verification code type: pic, sms, email, etc.
     */
    private String checkCodeType;

    /**
     * Business carrying parameters
     */
    private String param1;
    private String param2;
    private String param3;

}
