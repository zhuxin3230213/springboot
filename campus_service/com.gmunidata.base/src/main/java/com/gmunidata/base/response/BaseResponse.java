package com.gmunidata.base.response;

/**
 * 网络请求返回结果
 * @param <T>
 */
public class BaseResponse<T> {

    //成功编码
    public static final int SUCCESS_CODE = 200;

    //异常编码
    public static final int ERROR_CODE = 500;

    //无权限编码
    public static final int AUTH_CODE = 401;

    private String message;

    private int code = SUCCESS_CODE;

    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
