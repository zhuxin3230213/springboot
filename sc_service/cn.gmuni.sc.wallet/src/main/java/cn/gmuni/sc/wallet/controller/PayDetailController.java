package cn.gmuni.sc.wallet.controller;

import cn.gmuni.sc.wallet.service.IPayDetailService;
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
@Api(value = "/payDetail",description = "充值统计模块")
@RequestMapping(value = "/payDetail")
public class PayDetailController {

    @Autowired
    @Qualifier("payDetailService")
    IPayDetailService payDetailService;

    @ApiOperation(value = "获取充值列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "account",value = "所要查询学生编码"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<List<Map<String,Object>>> list(@RequestBody @ApiIgnore Map<String,Object> params){
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo(payDetailService.getPayDetailCount(params));
    }

    @ApiOperation(value = "获取充值图表展示")
    @RequestMapping(value = "/getShowChart",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date",value = "所要查询的日期类型"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间")})
    public List<Map<String,Object>> getShowChart(@RequestBody @ApiIgnore Map<String,Object> params){
        return payDetailService.getShowChart(params);
    }

    @ApiOperation(value = "按日期展示充值数据")
    @RequestMapping(value = "/getShowList",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "date",value = "所要查询的日期类型"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间")})
    public List<Map<String,Object>> getShowList(@RequestBody @ApiIgnore Map<String,Object> params){
        return  payDetailService.getShowList(params);
    }

}
