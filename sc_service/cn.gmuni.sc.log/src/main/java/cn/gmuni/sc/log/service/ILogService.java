package cn.gmuni.sc.log.service;

import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.model.OperatorLog;

import java.util.List;
import java.util.Map;

public interface ILogService {


    //查询日志信息
    List<OperatorLog> listLog(Map<String,Object> params);

    //查询日志详细信息
    Map<String,String>  getMessage(String id);

    //查看日访问人数
    List<Map<String,Object>> getDayPeopleNum(Map<String,Object> params);

    //查询模块的访问人数
    List<Map<String,Object>> getModuleOrTypePeopleNum(Map<String,Object> params);

    //查询当日访问人数
    List<Map<String,Object>> getTimePeopleNum(Map<String,Object> params);

    //查询登录日志信息
    List<LoginLog> listLoginLog(Map<String,Object> params);

    //查询登录日志详细信息
    Map<String,String>  getLoginMessage(String id);

    //获取类型饼图及登陆人数
    Map<String,Object> getLoginType(Map<String,Object> params);


    //获取一段时间内每天的新老用户登录人数
    List<Map<String,Object>> getUserNum(Map<String,Object> params);






}
