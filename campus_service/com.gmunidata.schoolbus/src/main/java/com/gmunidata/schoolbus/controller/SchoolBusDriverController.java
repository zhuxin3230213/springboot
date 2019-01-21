package com.gmunidata.schoolbus.controller;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.schoolbus.model.SchoolBusDriver;
import com.gmunidata.schoolbus.service.SchoolBusDriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Api(value = "schoolBusDriver",description = "校车司机管理控制类")
@RestController
@RequestMapping(value = "schoolBusDriver")
public class SchoolBusDriverController {

    @Autowired
    @Qualifier("schoolBusDriverServiceImpl")
    SchoolBusDriverService schoolBusDriverService;

    @ApiOperation(value = "添加校车司机信息")
    @PostMapping(value = "/add")
    public Content add(@RequestBody SchoolBusDriver driver){
        return schoolBusDriverService.addSchoolBusDriver(driver);
    }

    @ApiOperation(value = "修改校车司机信息")
    @PostMapping(value = "/update")
    public Content update(@RequestBody SchoolBusDriver driver){
        return schoolBusDriverService.updateSchoolBusDriver(driver);
    }

    @ApiOperation(value = "删除校车司机信息")
    @PostMapping(value = "/del")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "所要删除的id",required = true)})
    public Content del(@RequestBody @ApiIgnore Map<String,Object> params){
        List<String> ids =Arrays.asList(String.valueOf(params.get("ids")).split(","));
        return schoolBusDriverService.delSchoolBusDriver(ids);
    }

    @ApiOperation(value = "列表查询校车司机信息")
    @PostMapping(value = "/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)})
    public PageInfo<SchoolBusDriver> list(@RequestBody @ApiIgnore Map<String,Object> params){
        return new PageInfo<>(schoolBusDriverService.listSchoolBusDriver(params));
    }


}
