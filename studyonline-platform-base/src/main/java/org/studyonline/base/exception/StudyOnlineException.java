package org.studyonline.base.exception;

/**
 * @description Exception classes for online learning projects
 * @author Chengguang Li
 * @date 17/01/2024 13:00 PM
 * @version 1.0
 */

public class StudyOnlineException extends RuntimeException {

    private String errMessage;

    public StudyOnlineException() {
        super();
    }
    public StudyOnlineException(String errMessage) {
        super();
        this.errMessage = errMessage;
    }
    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(CommonError commonError){
        throw new StudyOnlineException(commonError.getErrMessage());
    }

    public static void cast(String errMessage){
        throw new StudyOnlineException(errMessage);
    }

}
