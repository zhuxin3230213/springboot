package cn.gmuni.sc.pay.controller;

import cn.gmuni.sc.pay.service.IPayCountService;
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

@Api(value ="/netPayCount",description = "网费统计")
@RestController
@RequestMapping(value = "/netPay")
public class PayCountController {

    @Autowired
    @Qualifier("payCountServiceImpl")
    IPayCountService  payCountServiceImpl;

    @ApiOperation(value = "获取网费统计模块的数据")
    @RequestMapping(value = "/getNetCount",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间")})
    public List<Map<String,Object>> getNetCount(@RequestBody @ApiIgnore Map<String,Object> params){
        return payCountServiceImpl.getNetCount(params);
    }

    @ApiOperation(value = "获取网络缴费趋势图数据")
    @RequestMapping(value = "/getNetCostTrend",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间"),
            @ApiImplicitParam(name = "date",value = "所要查询的日期类型")})
    public List<Map<String,Object>> getNetCostTrend(@RequestBody @ApiIgnore Map<String,Object> params){
        return payCountServiceImpl.getNetCostTrend(params);
    }

    @ApiOperation(value = "获取网络套餐类型对比数据")
    @RequestMapping(value = "/getSetMeal",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间")})
    public List<Map<String,Object>> getSetMeal(@RequestBody @ApiIgnore Map<String,Object> params){
        return payCountServiceImpl.getSetMeal(params);
    }

    @ApiOperation(value = "获取网络支付充值列表")
    @RequestMapping(value = "/selectPay",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "stuCode",value = "所要查询学生编码"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<List<Map<String,Object>>> selectPay(@RequestBody @ApiIgnore Map<String,Object> params){
        return new PageInfo(payCountServiceImpl.selectPay(params));
    }

    @ApiOperation(value = "获取网络支付统计列表")
    @RequestMapping(value = "/selectCount",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<List<Map<String,Object>>> selectCount(@RequestBody @ApiIgnore Map<String,Object> params){
        return new PageInfo(payCountServiceImpl.selectCount(params));
    }
    
}
