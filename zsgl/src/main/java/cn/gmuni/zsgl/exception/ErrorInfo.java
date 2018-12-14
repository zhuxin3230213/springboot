package cn.gmuni.zsgl.exception;

import org.springframework.stereotype.Component;

/**
 * @Author:ZhuXin
 * @Description:
 * 统一异常信息
 * @Date:Create in 15:42 2018/7/24
 * @Modified By:
 **/

@Component
public class ErrorInfo {

    private String time;//发生时间
    private String url;//访问Url
    private String error;//错误类型
    private String stackTrace;//错误的堆栈轨迹
    private int statusCode;//状态码


    public ErrorInfo() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public String toString() {
        return "ErrorInfo{" +
                "time='" + time + '\'' +
                ", url='" + url + '\'' +
                ", error='" + error + '\'' +
                ", stackTrace='" + stackTrace + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
