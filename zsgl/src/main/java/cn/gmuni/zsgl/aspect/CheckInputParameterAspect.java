package cn.gmuni.zsgl.aspect;

import cn.gmuni.zsgl.util.IllegalStrFilterUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:ZhuXin
 * @Description: 切面对HTTP传入的参数做防sql和非法字符串检测
 * @Date:Create in 17:33 2018/7/6
 * @Modified By:
 **/

//@Aspect
//@Component
public class CheckInputParameterAspect {
    private static final Log Logger = LogFactory.getLog(CheckInputParameterAspect.class);

    // 存在SQL注入风险
    private static final String IS_SQL_INJECTION = "输入参数存在SQL注入风险";

    private static final String UNVALIDATED_INPUT = "输入参数含有非法字符";

    private static final String ERORR_INPUT = "输入的参数非法";

    /**
     * 定义切入点:拦截controller层所有方法
     */
    @Pointcut("execution(public * cn.gmuni.zsgl.controller.*.*(..))")
    public void params() {
    }

    /**
     * 定义环绕通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("params()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object[] args = joinPoint.getArgs();// 参数
        String str = String.valueOf(args);

        if (!IllegalStrFilterUtil.sqlStrFilter(str)) {
            Logger.info(IS_SQL_INJECTION);
            new RuntimeException(ERORR_INPUT);
        }
        if (!IllegalStrFilterUtil.isIllegalStr(str)) {
            Logger.info(UNVALIDATED_INPUT);
            new RuntimeException(ERORR_INPUT);
        }
        Object result = joinPoint.proceed();
        Logger.info("当前调用接口-[" + request.getRequestURL() + "]");
        return result;
    }

}

