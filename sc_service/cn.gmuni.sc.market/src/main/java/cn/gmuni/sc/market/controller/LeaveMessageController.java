package cn.gmuni.sc.market.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.market.model.LeaveMessage;
import cn.gmuni.sc.market.service.ILeaveMessageService;
import cn.gmuni.sc.user.utils.UserUtils;
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

import java.util.*;

@Api(value = "/leave",description = "留言")
@RestController
@RequestMapping(value = "/leave")
public class LeaveMessageController {

    @Autowired
    ILeaveMessageService leaveMessageServiceImpl;

    @ApiOperation(value = "新增留言")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public BaseResponse<Map<String,Object>> add(@RequestBody LeaveMessage leaveMessage){
         return new BaseResponse<>(leaveMessageServiceImpl.addLeaveMessage(leaveMessage));
    }

    @ApiOperation(value = "删除留言")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "索要删除的留言信息id",required = true)
    })
    public Content del(@RequestBody @Ignore Map<String,Object> params){
        return leaveMessageServiceImpl.delLeaveMessage(Arrays.asList(String.valueOf(params.get("ids")).split(",")));
    }


    @ApiOperation(value = "刷新")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryType",value = "刷新方式",required = true),
            @ApiImplicitParam(name = "createTime",value = "所要刷新的时间点",required = true),
            @ApiImplicitParam(name = "marketId",value = "对应的商品id",required = true)
    })
    public BaseResponse<List<Map<String,Object>>> list(@RequestBody @Ignore Map<String,Object> params){
        return new BaseResponse<>(leaveMessageServiceImpl.listLeaveMessage(params));
    }

    @ApiOperation(value = "获取我的留言")
    @RequestMapping(value = "/getMyLeaveMessage",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryType",value = "刷新方式",required = true),
            @ApiImplicitParam(name = "createTime",value = "所要刷新的时间点",required = true),
    })
    public BaseResponse<List<Map<String,Object>>> getMyLeaveMessage(Map<String,Object> params){
        params.put("user",UserUtils.getLoginUserInfo().getIndentifier());
        return new BaseResponse<>(leaveMessageServiceImpl.listLeaveMessage(params));
    }






}
