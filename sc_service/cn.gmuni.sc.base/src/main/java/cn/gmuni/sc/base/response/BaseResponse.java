package cn.gmuni.sc.base.response;

import java.io.Serializable;

/**
 * 网络请求返回结果
 * @param <T>
 */
public class BaseResponse<T> implements Serializable {

    //成功编码
    public static final int SUCCESS_CODE = 200;

    //异常编码
    public static final int ERROR_CODE = 500;

    //无权限编码
    public static final int AUTH_CODE = 401;

    //空数据编码
    public static final int NONE_DATA = 1001;

    private String message;

    private int code = SUCCESS_CODE;

    public BaseResponse() {
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse( int code,String message) {
        this.message = message;
        this.code = code;
    }

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
