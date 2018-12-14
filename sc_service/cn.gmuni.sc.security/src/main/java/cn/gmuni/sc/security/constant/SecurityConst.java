package cn.gmuni.sc.security.constant;

public interface SecurityConst {

    String POST = "POST";

    //验证码登录方式
    int IDENTITY_TYPE_PHONE = 0;

    //微信登录方式
    int IDENTITY_TYPE_WECHAT = 1;

    String IDENTITY_TYPE = "identityType";

    String PHONE_NUMBER = "phoneNumber";

}
