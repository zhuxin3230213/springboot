package cn.gmuni.sc.log.utils;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.service.ILogRecordService;
import cn.gmuni.sc.utils.RequestUtils;
import cn.gmuni.utils.IdGenerator;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginLogger {

    private final static String LOGIN_LOG_OPERATOR_LOG = "LOGIN_LOG_OPERATOR_LOG";

    private static LoginLogger instance = null;

    private ILogRecordService logRecordService;

    private LoginLogger(){}

    private static LoginLogger getInstance(){
        if(instance==null){
            synchronized (SysLogger.class){
                if(instance==null){
                    instance = new LoginLogger();
                }
            }
        }
        return instance;
    }


    public static void log(LoginLog loginLog,HttpServletRequest request){
        completeLog(request,loginLog);
        saveLog(loginLog);
    }

    private static void completeLog(HttpServletRequest request,LoginLog log){
        log.setId(IdGenerator.getId());
        log.setCreateTime(new Date());
        log.setMethod(getMethod());
        updateRequestInfo(log,request);

    }

    private static void updateRequestInfo(LoginLog log,HttpServletRequest request){
        if(request!=null){
            log.setRequestMethod(request.getMethod().toLowerCase());
            log.setIp(RequestUtils.getIpAddress(request));
        }
    }



    /**
     * 获取被调用的方法
     * @return
     */
    private static String getMethod(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement e = stackTraceElements.length<5?stackTraceElements[3]:stackTraceElements[4];
        return e.getClassName()+"."+e.getMethodName()+"()";
    }


    public static void saveLog(LoginLog log){
        List<LoginLog> logs = new ArrayList<>();
        RedisCacheUtils build = RedisCacheUtils.build();
        if(build.hasKey(LOGIN_LOG_OPERATOR_LOG)){
            logs = RedisCacheUtils.build().get(LOGIN_LOG_OPERATOR_LOG, List.class);
        }
        logs.add(log);
        //如果日志条数大于100,则保存到数据库
        if(logs.size()>=2){
            getInstance().insertLogs(logs);
            build.put(LOGIN_LOG_OPERATOR_LOG,new ArrayList<>());
        }else{
            build.put(LOGIN_LOG_OPERATOR_LOG,logs);
        }
    }

    /**
     * 将日志插入到数据库中
     * @param logs
     */
    @Async
    protected  void insertLogs(List<LoginLog> logs){
        if(logRecordService==null){
            synchronized (SysLogger.class){
                if(logRecordService==null){
                    logRecordService = (ILogRecordService) ApplicationContextProvider.getBean("logRecordService");
                }
            }
        }
        logRecordService.insertLogin(logs);
    }
}
