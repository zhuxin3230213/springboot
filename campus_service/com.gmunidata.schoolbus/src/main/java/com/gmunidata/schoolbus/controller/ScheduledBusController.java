package com.gmunidata.schoolbus.controller;

import com.gmunidata.schoolbus.model.ScheduledBus;
import com.gmunidata.schoolbus.service.ScheduledBusService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/schedbus")
public class ScheduledBusController {


    @Autowired
    @Qualifier("scheduledBusServiceImpl")
    ScheduledBusService scheduledBusService;

    /**
     * app获取季节类型
     *
     * @return
     */
    @RequestMapping(value = "/check")
    public Map<String, String> checkSeasonType() {
        Map<String, String> map = new HashMap<>();
        List<String> seasonList = scheduledBusService.getSeasonList();
        if (seasonList.size() != 0) {
            if (!seasonList.contains("1") && !seasonList.contains("2")) {
                map.put("type", "general");
            } else {
                map.put("type", "unGeneral");
            }
        }
        return map;
    }

    /**
     * app获取时刻表
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/time")
    public Map<String, String> getSchoolBusTimeList(@RequestParam Map<String, String> params) {
        return scheduledBusService.schoolBusTimeList(params);
    }


    @RequestMapping(value = "/campusList")
    public List<Map<String, String>> getSchoolBusCampus() {
        List<Map<String, String>> schoolBusCampusList = scheduledBusService.getSchoolBusCampusList();
        List<String> list = new ArrayList<>(); //获取名称
        if (schoolBusCampusList.size() != 0) {
            for (Map<String, String> temp : schoolBusCampusList) {
                list.add(temp.get("name") + "," + temp.get("code"));
            }
        }

        //校区至少有两个
        List<Map<String, String>> maps = groupCampus(list); //组合
        return maps;
    }

    /**
     * 组合学校校区
     * 例如：长安 、咸阳、宝鸡
     * 期望组合结果：长安-咸阳 ，长安-宝鸡，咸阳-宝鸡，
     * 咸阳-长安， 宝鸡-长安 ，宝鸡-咸阳
     */
    private List<Map<String, String>> groupCampus(List<String> list) {
        List<Map<String, String>> caList = new ArrayList<>();
        String[] strings = new String[list.size()];
        String[] strings1 = list.toArray(strings); //list to array

        for (int i = 0; i < strings1.length; i++) {
            String[] strings3 = delete(i, strings1);//删除数组中指定的元素
            for (String temp : strings3) {
                Map<String, String> map = new HashMap<>();
                map.put("startCampus", strings1[i]);
                map.put("endCampus", temp);
                caList.add(map);
            }
        }
        return caList;
    }


    /**
     * 删除数组中指定的元素
     */
    public String[] delete(int index, String array[]) {
        //数组的删除其实就是覆盖前一位
        String[] arrNew = new String[array.length - 1];
        for (int i = 0; i < array.length - 1; i++) {
            if (i < index) {
                arrNew[i] = array[i];
            } else {
                arrNew[i] = array[i + 1];
            }
        }
        return arrNew;
    }


}

