package org.studyonline.ucenter.model.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 20/02/2024 2:10 pm
 */
@Data
public class AuthParamsDto {

    private String username;
    private String password;
    private String cellphone;
    //Verification code
    private String checkcode;
    //Verification code key
    private String checkcodekey;
    // Authentication type password: username password mode type sms: SMS mode type
    private String authType;
    //Additional data, as an extension, different authentication types can have different additional data. If the authentication type is SMS,
    //smsKey is included: sms:3d21042d054548b08477142bbca95cfa; clientId is included in all cases
    private Map<String, Object> payload = new HashMap<>();


}
