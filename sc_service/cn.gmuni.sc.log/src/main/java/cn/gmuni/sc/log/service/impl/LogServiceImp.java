package cn.gmuni.sc.log.service.impl;

import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.mapper.LogMapper;
import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.model.OperatorLog;
import cn.gmuni.sc.log.service.ILogService;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogServiceImp implements ILogService {

    private static final  String  MODULE="module";
    private static final  String  TYPE="type";
    private static final  String[] STR_TIMES = new String[]{"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00",
            "10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};

    @Autowired
    LogMapper logMapper;

    @Override
    public List<OperatorLog> listLog(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        List<OperatorLog> list = logMapper.listLog(params);
        SysLogModule[] values = SysLogModule.values();
        for (OperatorLog log:list){
            for (SysLogModule module:values) {
                if (log.getModule().equals(module.getName())){
                    log.setModule(module.getDesc());
                    break;
                }
            }
        }
        return list;
    }

    @Override
    public Map<String, String> getMessage(String id) {
        Map<String, String> map =logMapper.getMessage(id);
        if (map == null){
            map = new HashMap<>();
            map.put("params",null);
            map.put("logDesc",null);
        }
        return map;
    }

    @Override
    public List<Map<String,Object>> getDayPeopleNum(Map<String, Object> params) {
        List<Map<String,Object>> list =logMapper.getDayPeopleNum(params);
        if (list.size()!=0) {
            long size = logMapper.allPeopleNum(params);
            for (Map<String, Object> map : list) {
                map.put("totalNum", size);
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getModuleOrTypePeopleNum(Map<String, Object> params){
        List<Map<String,Object>> list = new ArrayList<>();
        if (MODULE.equals(params.get("type"))){
               list = logMapper.getModulePeopleNum(params);
            SysLogModule[] values = SysLogModule.values();
            for (Map<String, Object> map:list){
                for (SysLogModule module:values) {
                    if (map.get("module").equals(module.getName())){
                        map.put("module",module.getDesc());
                        break;
                    }
                }
            }
    }else  if (TYPE.equals(params.get("type"))){
        list = logMapper.geTypePeopleNum(params);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getTimePeopleNum(Map<String, Object> params) {
        List<Map<String, Object>> maps = logMapper.getTimePeopleNum(params);
        List<String> list = Arrays.asList(STR_TIMES);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (String str:list) {
            Map<String,Object> temp = new HashMap<>();
            temp.put("time",str);
            temp.put("num",0);
            mapList.add(temp);
        }
        for (Map<String,Object> temp :mapList) {
            for (Map<String,Object> map1:maps) {
                if (map1.get("time").equals(temp.get("time"))){
                    temp.put("num",map1.get("num"));
                }
            }
        }

        return mapList;
    }

    @Override
    public List<LoginLog> listLoginLog(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return logMapper.listLoginLog(params);
    }

    @Override
    public Map<String, String> getLoginMessage(String id) {
        Map<String, String> map =logMapper.getLoginMessage(id);
        if (map == null){
            map = new HashMap<>();
            map.put("params",null);
            map.put("logDesc",null);
        }
        return map;
    }

    @Override
    public Map<String, Object> getLoginType(Map<String, Object> params) {
        String date = String.valueOf(params.get("date"));
        List<Map<String, Object>> loginType = logMapper.getLoginType(params);
        List<Map<String, Object>> logType = logMapper.getLogType(params);
        List<Map<String, Object>> dayPeopleNum = new ArrayList<>();
        if (date != null && !"".equals(date)){
            if (date.equals("0")){
                dayPeopleNum = logMapper.getTimeLoginPeopleNum(params);
            }else{
                dayPeopleNum = logMapper.getDayLoginPeopleNum(params);
            }
        }
        Map<String, Object> map = new HashMap<>();
          map.put("loginType",loginType);
          map.put("logType",logType);
          map.put("dayPeopleNum",dayPeopleNum);
        return map;
    }

    @Override
    public List<Map<String, Object>> getUserNum(Map<String, Object> params) {
        return logMapper.getUserNum(params);
    }


}
