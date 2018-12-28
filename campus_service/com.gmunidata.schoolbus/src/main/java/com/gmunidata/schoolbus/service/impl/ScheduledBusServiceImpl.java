package com.gmunidata.schoolbus.service.impl;

import com.gmunidata.schoolbus.mapper.ScheduledBusMapper;
import com.gmunidata.schoolbus.service.ScheduledBusService;
import com.gmunidata.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduledBusServiceImpl implements ScheduledBusService {

    @Autowired
    ScheduledBusMapper scheduledBusMapper;

    @Override
    public List<String> getSeasonList() {
        return scheduledBusMapper.getSchoolSeasonList();
    }

    @Override
    public Map<String, String> schoolBusTimeList(Map<String, String> map) {
        Map<String, String> schoolBus = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("season", map.get("season"));
        queryParams.put("week", map.get("week"));
        queryParams.put("startCampus", map.get("startCampus"));
        queryParams.put("endCampus", map.get("endCampus"));
        List<Map<String, String>> schoolBusTimeList = scheduledBusMapper.getSchoolBusTimeList(queryParams);
        if (schoolBusTimeList.size() != 0){
            String address = schoolBusTimeList.get(0).get("address");
            //将时间进行排序
            List<String> list = ascTime(schoolBusTimeList);
            //将时间拼接成字符串
            String timeList = String.join("   ", list);
            schoolBus.put("address", address);
            schoolBus.put("timeList",timeList);
        }else {
            schoolBus.put("address","");
            schoolBus.put("timeList","暂无安排");
        }
        return schoolBus;
    }

    @Override
    public List<Map<String, String>> getSchoolBusCampusList() {
        return  scheduledBusMapper.getSchoolCampusList();
    }

    /**
     * 将一组 HH:mm 字符串数据进行排序
     * @param schoolBusTimeList
     * @return
     */
    private List<String> ascTime(List<Map<String, String>> schoolBusTimeList) {
        List<Long> timeList = new ArrayList<>();
        for (Map<String, String> temp : schoolBusTimeList) {
            Date sTime = DateUtils.string2Date(temp.get("sTime"), DateUtils.HOUR_MIN);
            long time = sTime.getTime();
            timeList.add(time);
        }
        Collections.sort(timeList); //升序排列
        List<String> ascTime = new ArrayList<>();
        for (Long temp : timeList) {
            Date date = DateUtils.long2Date(temp);
            String s = DateUtils.date2String(date, DateUtils.HOUR_MIN);
            ascTime.add(s);
        }
        return ascTime;
    }
}
