package org.studyonline.base.exception;

/**
 * @description Generic error messages
 * @author  Chengguang Li
 * @date 17/01/2024 13:00 PM
 * @version 1.0
 */
public enum CommonError {
    UNKOWN_ERROR("The execution process is abnormal, please try again."),
    PARAMS_ERROR("Illegal parameter"),
    OBJECT_NULL("Object is empty"),
    QUERY_NULL("Query result is empty"),
    REQUEST_NULL("The request parameters are empty");

    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    private CommonError( String errMessage) {
        this.errMessage = errMessage;
    }
}
