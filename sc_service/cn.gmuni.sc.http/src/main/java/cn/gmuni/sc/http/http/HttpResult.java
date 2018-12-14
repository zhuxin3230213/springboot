package cn.gmuni.sc.http.http;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create in 14:07 2018/8/15
 * @Modified By:
 **/


public class HttpResult {
    // 响应码
    private Integer code;

    // 响应体
    private String body;

    public HttpResult() {
        super();
    }

    public HttpResult(Integer code, String body) {
        super();
        this.code = code;
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
