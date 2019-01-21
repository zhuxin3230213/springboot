package cn.gmuni.sc.log.constant;

/**
 * 登录日志类型
 */
public enum  LoginLogType {

    LOGIN_LOG("0","登录"),
    EXCEPTION_LOG("1","异常");

    private String code;

    private String desc;

    LoginLogType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return code;
    }

    private String getDesc(){
        return desc;
    }

}
