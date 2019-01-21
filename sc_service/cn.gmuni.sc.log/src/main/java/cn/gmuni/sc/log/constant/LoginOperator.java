package cn.gmuni.sc.log.constant;

/**
 * 登录操作
 */
public enum  LoginOperator {

    LOGIN("0","登录"),
    LOGOUT("1","退出登录");

    private String code;

    private String desc;

    LoginOperator(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
