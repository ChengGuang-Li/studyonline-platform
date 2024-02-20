package org.studyonline.gateway.config;

import java.io.Serializable;

/**
 * @Description: Error response parameter wrapper
 * @Author: Chengguang Li
 * @Date: 20/02/2024 11:49 pm
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}

