package org.studyonline.captcha.service;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 29/02/2024 9:55 pm
 */
public interface SendCodeService {

    /**
     *
     * @param email
     */
    void sendEMail(String email);
}
