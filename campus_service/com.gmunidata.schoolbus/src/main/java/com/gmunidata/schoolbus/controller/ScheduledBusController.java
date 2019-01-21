package com.gmunidata.schoolbus.controller;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.schoolbus.model.ScheduledBus;
import com.gmunidata.schoolbus.service.ScheduledBusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api(value = "scheduledBus", description = "校车运营管理控制类")
@RestController
@RequestMapping(value = "/schedbus")
public class ScheduledBusController {


    @Autowired
    @Qualifier("scheduledBusServiceImpl")
    ScheduledBusService scheduledBusService;


    @ApiOperation(value = "添加校车运营方案")
    @PostMapping(value = "/add")
    public Content add(@RequestBody ScheduledBus scheduledBus) {
        return scheduledBusService.addScheduledBus(scheduledBus);
    }

    @ApiOperation(value = "修改校车运营方案")
    @PostMapping(value = "/update")
    public Content update(@RequestBody ScheduledBus scheduledBus) {
        return scheduledBusService.updateScheduledBus(scheduledBus);
    }

    @ApiOperation(value = "删除校车运营方案")
    @PostMapping(value = "/del")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "索要删除的id", required = true)})
    public Content del(@RequestBody @ApiIgnore Map<String, Object> params) {
        List<String> ids = Arrays.asList(String.valueOf(params.get("ids")).split(","));
        return scheduledBusService.delScheduledBus(ids);
    }

    @ApiOperation(value = "列表查询校车运营方案")
    @PostMapping(value = "/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)})
    public PageInfo<Map<String, Object>> list(@RequestBody @ApiIgnore Map<String, Object> params) {
        return new PageInfo<>(scheduledBusService.listScheduledBus(params));
    }

    @ApiOperation(value = "所有校车列表")
    @PostMapping(value = "/getSchoolBusCode")
    public List<String> getSchoolBusCode() {
        return scheduledBusService.getSchoolBusCode();
    }

    @ApiOperation(value = "所有校车司机列表")
    @PostMapping(value = "/getSchoolBusDrivceCode")
    public List<Map<String, Object>> getSchoolBusDrivceCode() {
        return scheduledBusService.getSchoolBusDrivceCode();
    }

    @ApiOperation(value = "导出通勤班车时刻表")
    @PostMapping(value = "/outScheduledBus")
    public void outScheduledBus(HttpServletResponse res) {
        scheduledBusService.outScheduledBus(res);
    }

    /**
     * app获取季节类型
     *
     * @return
     */
    @ApiOperation(value = "app获取季节类型")
    @RequestMapping(value = "/check",method = RequestMethod.GET)
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
    @ApiOperation(value = "app获取时刻表")
    @RequestMapping(value = "/time",method = RequestMethod.GET)
    public Map<String, String> getSchoolBusTimeList(@RequestParam Map<String, String> params) {
        return scheduledBusService.schoolBusTimeList(params);
    }

    /**
     * app获取校区列表
     *
     * @return
     */
    @ApiOperation(value = "app获取校区列表")
    @RequestMapping(value = "/campusList",method = RequestMethod.GET)
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

