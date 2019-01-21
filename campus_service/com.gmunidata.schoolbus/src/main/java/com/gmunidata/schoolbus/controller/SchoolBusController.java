package com.gmunidata.schoolbus.controller;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.schoolbus.model.SchoolBus;
import com.gmunidata.schoolbus.service.SchoolBusService;
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

@Api(value = "schoolBus",description = "校车管理控制类")
@RestController
@RequestMapping(value = "schoolBus")
public class SchoolBusController {

    @Autowired
    @Qualifier("schoolBusServiceImpl")
    SchoolBusService schoolBusService;

    @ApiOperation(value = "添加校车信息")
    @PostMapping(value = "/add")
    public Content add(@RequestBody SchoolBus bus){
        return schoolBusService.addSchoolBus(bus);
    }

    @ApiOperation(value = "修改校车信息")
    @PostMapping(value = "/update")
    public Content update(@RequestBody SchoolBus bus){
        return schoolBusService.updateSchoolBus(bus);
    }

    @ApiOperation(value = "删除校车信息")
    @PostMapping(value = "/del")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "所要删除的id",required = true)})
    public Content del(@RequestBody @ApiIgnore Map<String,Object> params){
        List<String> ids =Arrays.asList(String.valueOf(params.get("ids")).split(","));
        return schoolBusService.delSchoolBus(ids);
    }

    @ApiOperation(value = "列表查询校车信息")
    @PostMapping(value = "/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)})
    public PageInfo<SchoolBus> list(@RequestBody @ApiIgnore Map<String,Object> params){
        return new PageInfo<>(schoolBusService.listSchoolBus(params)) ;
    }

}
