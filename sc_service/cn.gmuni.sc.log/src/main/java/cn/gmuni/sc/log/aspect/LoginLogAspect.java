package cn.gmuni.sc.log.aspect;

import cn.gmuni.sc.log.constant.LoginLogType;
import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.utils.SysLogger;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.RequestUtils;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import cn.gmuni.utils.IdGenerator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LoginLogAspect {

    @Pointcut("@annotation(cn.gmuni.sc.log.anntation.LoginLog)")
    public void logPointCut(){}

    /**
     * 保存常规日志
     * @param point
     */
    @Before("logPointCut()")
    public void saveSysLog(JoinPoint point){
        //获取调用的方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        Logger logger = LoggerFactory.getLogger(signature.getClass());
        LoginLog log = getLog(point, signature);
        logger.info(log.getLogDesc());
        saveLog(log);

    }


    /**
     * 保存异常日志
     * @param point
     * @param e
     */
    @AfterThrowing(pointcut = "logPointCut()",throwing = "e")
    public void doException(JoinPoint point ,Throwable e){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Logger logger = LoggerFactory.getLogger(signature.getClass());
        LoginLog log = getLog(point, signature);
        log.setLogType(LoginLogType.EXCEPTION_LOG.getCode());
        String error = SysLogger.getExceptionStr(e);
        log.setLogDesc(log.getLogDesc()+"\n"+error);
        logger.error(error);
        saveLog(log);
    }

    /**
     * 保存日志
     * @param log
     */
    private void saveLog(LoginLog log){

    }


    /**
     * 获取日志内容
     * @param point
     * @param signature
     * @return
     */
    private LoginLog getLog(JoinPoint point, MethodSignature signature) {
        Method method = signature.getMethod();
        LoginLog log = new LoginLog();
        //获取传入的参数
        cn.gmuni.sc.log.anntation.LoginLog loginLog = method.getAnnotation(cn.gmuni.sc.log.anntation.LoginLog.class);
        String desc = loginLog.desc();
        String loginMethod = loginLog.method().getCode();
        String type = loginLog.type().getCode();
        String operator = loginLog.operator().getCode();
        //请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = className+"."+method.getName()+"()";
        //请求的参数
        Object[] args = point.getArgs();
        String params = JsonUtil.object2Json(args);
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = RequestUtils.getIpAddress(request);
        String requestMethod = request.getMethod();
        if (UserUtils.getLoginUser()!=null && UserUtils.getLoginUser().getSchool()!=null){
            log.setSchoolCode(UserUtils.getLoginUser().getSchool());
        }
        log.setId(IdGenerator.getId());
        log.setLogDesc(desc);
        log.setLoginType(loginMethod);
        log.setOperator(operator);
        log.setMethod(methodName);
        log.setParams(params);
        log.setIp(ip);
        log.setRequestMethod(requestMethod.toLowerCase());
        log.setCreateTime(new Date());
        log.setLogType(type);
        return log;
    }
}
