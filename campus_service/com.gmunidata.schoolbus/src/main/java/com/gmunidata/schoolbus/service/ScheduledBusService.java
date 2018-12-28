package com.gmunidata.schoolbus.service;

import java.util.List;
import java.util.Map;

public interface ScheduledBusService {
    //季节列表
    List<String> getSeasonList();

    //获取时刻表
    Map<String ,String > schoolBusTimeList(Map<String ,String > map);

    //获取校区列表
    List<Map<String,String>> getSchoolBusCampusList();
}
