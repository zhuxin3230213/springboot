package cn.gmuni.sc.log.anntation;

import cn.gmuni.sc.log.constant.LoginLogType;
import cn.gmuni.sc.log.constant.LoginMethod;
import cn.gmuni.sc.log.constant.LoginOperator;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLog {
    String desc() default "";
    LoginLogType type();
    LoginMethod method() default  LoginMethod.PHONE;
    LoginOperator operator() default  LoginOperator.LOGIN;
}
