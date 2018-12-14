package cn.gmuni.sc.log.constant;

public enum  SysLogType {

    LOGIN_LOG("0","登录"),
    VISIT_LOG("1","访问"),
    OPERATOR_LOG("2","操作"),
    EXCEPTION_LOG("3","异常"),
    UPDATE_LOG("4","修改"),
    ADD_LOG("5","新增"),
    REMOVE_LOG("6","删除");

    private String code;

    private String desc;

    SysLogType(String code, String desc) {
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
