package org.studyonline.base.model;

import lombok.Data;

@Data
public class RestResponse<T> {
    /**
     * Corresponding coding: 0 means normal -1 means error
     */
    private int code;
    /**
     * Respond to prompt information
     */
    private String msg;
    /**
     * Response content
     */
    private T result;

    public RestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestResponse() {
        this(0, "success");
    }

    /**
     * Encapsulation of error messages
     */
    public static <T> RestResponse<T> validfail() {
        RestResponse<T> response = new RestResponse<>(-1,"Failed");
        return response;
    }

    public static <T> RestResponse<T> validfail(String msg) {
        RestResponse<T> response = new RestResponse<>();
        response.setCode(-1);
        response.setMsg(msg);
        return response;
    }

    public static <T> RestResponse<T> validfail(String msg, T result) {
        RestResponse<T> response = new RestResponse<>();
        response.setCode(-1);
        response.setMsg(msg);
        response.setResult(result);
        return response;
    }

    /**
     * Encapsulation of normal information
     */
    public static <T> RestResponse<T> success() {
        return new RestResponse<>();
    }

    public static <T> RestResponse<T> success(T result) {
        RestResponse<T> response = new RestResponse<>();
        response.setResult(result);
        return response;
    }

    public static <T> RestResponse<T> success(String msg, T result) {
        RestResponse<T> response = new RestResponse<>();
        response.setMsg(msg);
        response.setResult(result);
        return response;
    }
}
