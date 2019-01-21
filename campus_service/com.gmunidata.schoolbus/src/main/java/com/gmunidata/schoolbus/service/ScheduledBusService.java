package com.gmunidata.schoolbus.service;

import com.gmunidata.base.response.Content;
import com.gmunidata.schoolbus.model.ScheduledBus;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ScheduledBusService {
    //季节列表
    List<String> getSeasonList();

    //获取时刻表
    Map<String ,String > schoolBusTimeList(Map<String ,String > map);

    //获取校区列表
    List<Map<String,String>> getSchoolBusCampusList();

    //新增校车运营信息
    Content addScheduledBus(ScheduledBus scheduledBus);

    //编辑校车运营信息
    Content updateScheduledBus(ScheduledBus scheduledBus);

    //删除校车运营信息
    Content delScheduledBus(List<String> ids);

    //查询校车运营列表
    List<Map<String,Object>> listScheduledBus(Map<String,Object> params);

    //获取所有校车车牌号
    List<String> getSchoolBusCode();

    //获取所有司机工牌号
    List<Map<String,Object>> getSchoolBusDrivceCode();

    //导出班车时刻表
    void outScheduledBus(HttpServletResponse res);
}
