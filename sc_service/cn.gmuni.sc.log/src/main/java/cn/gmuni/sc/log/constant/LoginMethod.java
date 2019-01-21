package cn.gmuni.sc.log.constant;

/**
 * 登录方式
 */
public enum LoginMethod {

    PHONE("0","手机号登录"),
    WECHAT("1","微信登录");

    private String code;

    private String desc;

    LoginMethod(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }
}
