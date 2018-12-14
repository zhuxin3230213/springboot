package cn.gmuni.sc.log.mapper;

import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.model.OperatorLog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LogMapper {

    //查询日志信息
    List<OperatorLog> listLog(Map<String,Object> params);

    //查询日志详细信息
    Map<String,String>  getMessage(String id);

    //查询当日访问人数
    List<Map<String,Object>> getDayPeopleNum(Map<String,Object> params);

    //查询当日访问人数
    List<Map<String,Object>> getTimePeopleNum(Map<String,Object> params);

    //查询模块的访问人数
    List<Map<String,Object>> getModulePeopleNum(Map<String,Object> params);

    //查询日志类型的访问人数
    List<Map<String,Object>> geTypePeopleNum(Map<String,Object> params);

    //查询当日访问人数
    long allPeopleNum(Map<String, Object> params);


    //查询登录日志信息
    List<LoginLog> listLoginLog(Map<String,Object> params);

    //查询登录日志详细信息
    Map<String,String>  getLoginMessage(String id);

    //获取登录类型饼图
    List<Map<String,Object>> getLoginType(Map<String,Object> params);

    //获取登录是否正常类型饼图
    List<Map<String,Object>> getLogType(Map<String,Object> params);

    //获取每天的各时间点的登陆人数及每个类型的登录人数)
    List<Map<String,Object>> getTimeLoginPeopleNum(Map<String,Object> params);

    //获取一段时间内每天的登录人数(及每个类型的登录人数)
    List<Map<String,Object>> getDayLoginPeopleNum(Map<String,Object> params);

    //获取一段时间内每天的新老用户登录人数
    List<Map<String,Object>> getUserNum(Map<String,Object> params);

}
