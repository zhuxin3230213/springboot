package cn.gmuni.sc.log.anntation;

import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String desc() default "";

    SysLogModule module();

    SysLogType type() default  SysLogType.OPERATOR_LOG;

}
