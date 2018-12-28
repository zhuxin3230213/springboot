package com.gmunidata.newsNotice.controller;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.model.Info;
import com.gmunidata.newsNotice.service.IMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Map;

@Api(value = "/message",description = "消息管理控制类")
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    @Qualifier("messageServiceImpl")
    IMessageService messageServiceImpl;

    @ApiOperation(value = "添加新的消息")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Content  addInfo(@RequestBody Info info){
        return messageServiceImpl.addInfo(info);
    }

    @ApiOperation(value = "查询消息列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupCode",value = "对象"),
            @ApiImplicitParam(name = "content",value = "内容"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "结束时间"),
            @ApiImplicitParam(name = "releaseUser",value = "发布者", required = true),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)})
    public PageInfo<Map<String,Object>> getListInfo(@RequestBody @ApiIgnore Map<String,Object> params){
        return  new PageInfo<>(messageServiceImpl.getListInfo(params));
    }

    @ApiOperation(value = "删除消息")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "索要删除的消息的id", required = true)})
    public Content delInfo(@RequestBody @ApiIgnore Map<String,Object> params){
        return messageServiceImpl.delInfo(Arrays.asList(String.valueOf(params.get("ids")).split(",")));
    }

    @ApiOperation(value = "重新发送")
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public Content delInfo(@RequestBody Info info){
        return messageServiceImpl.updateStatus(info);
    }
}
