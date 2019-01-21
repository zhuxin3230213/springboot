package com.gmunidata.newsNotice.controller;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.model.InfoGroup;
import com.gmunidata.newsNotice.service.IInfoGroupService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Api(value = "/group",description = "组管理控制类")
@RestController
@RequestMapping(value = "/group")
public class InfoGroupController {

    @Autowired
    @Qualifier("infoGroupServiceImpl")
    IInfoGroupService infoGroupServiceImpl;

    @ApiOperation(value = "获取学院专业班级树")
    @RequestMapping(value = "/getClassTree",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "登录人员")
    })
   public List<Map<String,Object>> getClassTree(@RequestBody @ApiIgnore Map<String,Object> params){
       return  infoGroupServiceImpl.getClassTree(String.valueOf(params.get("code")));
   }

    @ApiOperation(value = "获取组列表")
    @RequestMapping(value = "/selectGroup",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createPeople",value = "创建者")
    })
    public List<Map<String,Object>> selectGroup(@RequestBody @ApiIgnore Map<String,Object> params){
       return infoGroupServiceImpl.selectGroup(params);
    }

    @ApiOperation(value = "获取组成员")
    @RequestMapping(value = "/getGroupStudent",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupCode",value = "组的编码信息",required = true),
            @ApiImplicitParam(name = "createPeople",value = "创建者"),
            @ApiImplicitParam(name = "flag",value = "标识符，zy:学院专业,bj:班级,group:自定义组", required = true),
            @ApiImplicitParam(name = "currentPage", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true)})
    public PageInfo<Map<String,Object>> getGroupStudent(@RequestBody @ApiIgnore Map<String,Object> params){
        List<Map<String,Object>> list = infoGroupServiceImpl.getGroupStudent(params);
        if (list.size() == 0 ){
            return null;
        }
        return  new PageInfo<>(list);
    }

    @ApiOperation(value = "添加组")
    @RequestMapping(value = "/addInfoGroup",method = RequestMethod.POST)
    public Content addInfoGroup(@RequestBody InfoGroup infoGroup){
      return  infoGroupServiceImpl.addInfoGroup(infoGroup);
    }


    @ApiOperation(value = "删除组")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "所要删除的组的code" , required = true),
            @ApiImplicitParam(name = "createPeople",value = "所要删除的组的创建人", required = true)
    })
    public Content delGroup(@RequestBody @ApiIgnore Map<String,Object> params){
        return infoGroupServiceImpl.delGroup(params);
    }



}
