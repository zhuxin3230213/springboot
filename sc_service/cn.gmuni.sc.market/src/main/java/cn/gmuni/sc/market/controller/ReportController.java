package cn.gmuni.sc.market.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.market.model.Report;
import cn.gmuni.sc.market.service.IReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "/report",description = "市场举报")
@RestController
@RequestMapping(value = "/report")
public class ReportController {

    @Autowired
    IReportService reportServiceImpl;

    @ApiOperation(value = "新增举报记录")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public BaseResponse<Map<String, Object>> add(@RequestBody Report report){
        return new BaseResponse<>(reportServiceImpl.addReport(report)) ;
    }

    @ApiOperation(value = "锁定和解锁商品状态")
    @RequestMapping(value = "/updateMarketStatus",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "要改变的状态"),
            @ApiImplicitParam(name = "id",value = "所要锁定和解锁的商品的id")
    })
    public Content updateMarketStatus(@RequestBody @Ignore Map<String,Object> params){
        return reportServiceImpl.updateMarketStatus(params);
    }

    @ApiOperation(value = "列表查询举报信息")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题"),
            @ApiImplicitParam(name = "name", value = "姓名"),
            @ApiImplicitParam(name = "reason", value = "原因"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public List<Map<String,Object>> list(@RequestBody @Ignore Map<String,Object> params){
        return reportServiceImpl.listReport(params);
    }

    @ApiOperation(value = "获取某商品详情")
    @RequestMapping(value = "/getMarket",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "要查询的商品的id",required = true)
    })
    public Map<String,Object> getMarket(@RequestBody @Ignore Map<String,Object> params){
        return reportServiceImpl.getMarket(String.valueOf(params.get("id")));
    }


}
