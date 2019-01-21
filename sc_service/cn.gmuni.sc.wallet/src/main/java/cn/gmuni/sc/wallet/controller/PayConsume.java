package cn.gmuni.sc.wallet.controller;

import cn.gmuni.sc.wallet.service.IPayConsumeService;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@RestController
@Api(value = "/payConsume",description = "消费统计模块")
@RequestMapping(value = "/payConsume")
public class PayConsume {

    @Autowired
    @Qualifier("payConsumeService")
    IPayConsumeService payConsumeService;

    @ApiOperation(value = "获取消费列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "buyerId",value = "所要查询学生编码"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<List<Map<String,Object>>> list(@RequestBody @ApiIgnore Map<String,Object> params){
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo(payConsumeService.getPayConsumeCount(params));
    }

    @ApiOperation(value = "按学校获取消费数据")
    @RequestMapping(value = "/showConsumeCount",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间")})
    public List<Map<String,Object>> showConsumeCount(@RequestBody @ApiIgnore Map<String,Object> params){
        return payConsumeService.showConsumeCount(params);
    }

    @ApiOperation(value = "按类型获取消费数据")
    @RequestMapping(value = "/getCountByType",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "date",value = "日期类型"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间")})
    public List<Map<String,Object>> getCountByType(@RequestBody @ApiIgnore Map<String,Object> params){
        return payConsumeService.getCountByType(params);
    }

    @ApiOperation(value = "时间段获取人数和订单数")
    @RequestMapping(value = "/getDayPeopleAndOrder",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "date",value = "日期类型"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间")})
    public List<Map<String,Object>> getDayPeopleAndOrder(@RequestBody @ApiIgnore Map<String,Object> params){
        return payConsumeService.getDayPeopleAndOrder(params);
    }




}
