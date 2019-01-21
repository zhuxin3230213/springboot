package com.gmunidata.schoolbus.mapper;

import com.gmunidata.schoolbus.model.ScheduledBus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public interface ScheduledBusMapper {
    //新增校车运营信息
    int addScheduledBus(ScheduledBus scheduledBus);

    //编辑校车运营信息
    int updateScheduledBus(ScheduledBus scheduledBus);

    //删除校车运营信息
    int delScheduledBus(List<String> ids);

    //查询校车运营列表
    List<Map<String,Object>> listScheduledBus(Map<String,Object> params);

    //获取所有校车车牌号
    List<String> getSchoolBusCode();

    //获取所有司机工牌号
    List<Map<String,Object>> getSchoolBusDrivceCode();

    //获取对应的季节
    List<String> checkSeason(ScheduledBus scheduledBus);

    //导出查询
    List<Map<String,Object>> outScheduledBus();

    //app获取所有季节
    List<String> getSchoolSeasonList();
    //app获取时刻表
    List<Map<String ,String>> getSchoolBusTimeList(Map<String ,String > params);
    //app获取校区列表
    List<Map<String,String>> getSchoolCampusList();
}
