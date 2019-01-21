package cn.gmuni.sc.market.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.market.model.Market;
import cn.gmuni.sc.market.service.IMarketService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Api(value = "/market",description = "集市")
@RestController
@RequestMapping(value = "/market")
public class MarketController {

    @Autowired
    IMarketService marketServiceImpl;

    @ApiOperation(value = "新增二手商品")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public BaseResponse<Map<String,Object>> add(@RequestBody Market market){
        return  new BaseResponse<>(marketServiceImpl.addMarket(market));
    }

    @ApiOperation(value = "修改商品信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public BaseResponse<Map<String,Object>> update(@RequestBody Market market){
        return  new BaseResponse<>(marketServiceImpl.updateMarket(market));
    }

    @ApiOperation(value = "修改商品出售状态为已出售")
    @RequestMapping(value = "/updateSaleStatus",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "要标记的商品",required = true)
    })
    public BaseResponse<Map<String,Object>> updateSaleStatus(@RequestBody @Ignore Map<String,Object> params){
        return  new BaseResponse<>(marketServiceImpl.updateSaleStatus(params));
    }

    @ApiOperation(value = "删除商品信息")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "要删除的的商品",required = true)
    })
    public BaseResponse<Map<String,Object>> del(@RequestBody @Ignore Map<String,Object> params){
        return  new BaseResponse<>(marketServiceImpl.delMarket(Arrays.asList(String.valueOf(params.get("id")))));
    }

    @ApiOperation(value = "列表查询商品")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型"),
            @ApiImplicitParam(name = "saleStatus", value = "出售状态"),
            @ApiImplicitParam(name = "user", value = "用户"),
            @ApiImplicitParam(name = "queryType", value = "刷新类型"),
            @ApiImplicitParam(name = "updateTime", value = "最新一条的时间")})
    public BaseResponse<List<Map<String,Object>>> list(@RequestBody @Ignore Map<String,Object> params){
        return new BaseResponse<>(marketServiceImpl.listMarket(params));
    }

    @ApiOperation(value = "获取某商品的其余信息")
    @RequestMapping(value = "/getMarketById",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "要查询的商品的id",required = true)
    })
    public BaseResponse<Map<String,Object>> getMarketById(@RequestBody @Ignore Map<String,Object> params){
        return new BaseResponse<>(marketServiceImpl.getMarketById(String.valueOf(params.get("id"))));
    }



}
