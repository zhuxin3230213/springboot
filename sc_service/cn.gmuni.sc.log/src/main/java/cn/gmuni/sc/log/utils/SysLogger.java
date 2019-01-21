package cn.gmuni.sc.log.utils;


import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.log.model.OperatorLog;
import cn.gmuni.sc.log.service.ILogRecordService;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.RequestUtils;
import cn.gmuni.utils.IdGenerator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysLogger {

    private final static String SYS_LOG_OPERATOR_LOG = "SYS_LOG_OPERATOR_LOG";

    private static SysLogger instance = null;

    private ILogRecordService logRecordService;

    private SysLogger(){}

    private static SysLogger getInstance(){
        if(instance==null){
            synchronized (SysLogger.class){
                if(instance==null){
                    instance = new SysLogger();
                }
            }
        }
        return instance;
    }

    /**
     * 将日志插入到数据库中
     * @param logs
     */
    @Async
    protected  void insertLogs(List<OperatorLog> logs){
        if(logRecordService==null){
            synchronized (SysLogger.class){
                if(logRecordService==null){
                    logRecordService = (ILogRecordService) ApplicationContextProvider.getBean("logRecordService");
                }
            }
        }
        logRecordService.insert(logs);
    }

    public static void info(String msg, SysLogModule module, SysLogType type){
        OperatorLog log = buildLog();
        log.setLogDesc(msg);
        log.setType(type.getCode());
        log.setModule(module.getName());
        saveLog(log);
    }

    public static void error(Throwable e,SysLogModule module,SysLogType type){
        OperatorLog log = buildLog();
        log.setLogDesc(getExceptionStr(e));
        log.setType(type.getCode());
        log.setModule(module.getName());
        saveLog(log);
    }

    public static void error(String error,SysLogModule module,SysLogType type){
        OperatorLog log = buildLog();
        log.setType(type.getCode());
        log.setModule(module.getName());
        log.setLogDesc(error);
        saveLog(log);
    }

    public static void error(String msg,Throwable e,SysLogModule module,SysLogType type){
        error(msg+"\n"+getExceptionStr(e),module,type);
    }


    public static void saveLog(OperatorLog log){
        List<OperatorLog> logs = new ArrayList<>();
        RedisCacheUtils build = RedisCacheUtils.build();
        if(build.hasKey(SYS_LOG_OPERATOR_LOG)){
            logs = RedisCacheUtils.build().get(SYS_LOG_OPERATOR_LOG, List.class);
        }
        logs.add(log);
        //如果日志条数大于100,则保存到数据库
        if(logs.size()>=2){
            getInstance().insertLogs(logs);
            build.put(SYS_LOG_OPERATOR_LOG,new ArrayList<>());
        }else{
            build.put(SYS_LOG_OPERATOR_LOG,logs);
        }
    }

    public static String getExceptionStr(Throwable e){
        StringWriter sw = null;
        PrintWriter pw  = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "ErrorInfoFromException";
        }finally {
            try {
                if(sw!=null){
                    sw.close();
                }
                if(pw!=null){
                    pw.close();
                }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    private static OperatorLog buildLog(){
        OperatorLog log = new OperatorLog();
        log.setId(IdGenerator.getId());
        log.setCreateTime(new Date());
        log.setMethod(getMethod());
        updateRequestInfo(log);
        return log;
    }

    private static void updateRequestInfo(OperatorLog log){
        HttpServletRequest request = getRequest();
        if(request!=null){
            log.setRequestMethod(request.getMethod().toLowerCase());
            log.setIp(RequestUtils.getIpAddress(request));
            log.setUrl(request.getRequestURI());
            ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo(request);
            log.setUsername(loginUserInfo.getIndentifier());
            ThirdPartUser loginUser = UserUtils.getLoginUser(request);
            log.setSchoolCode(loginUser.getSchool());
//          log.setParams(RequestUtils.getParamsStr(request));
        }
    }

    /**
     * 获取网络请求
     * @return
     */
    private static HttpServletRequest getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
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


}
