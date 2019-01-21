package com.gmunidata.schedule.controller;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;

import com.gmunidata.schedule.model.CourseTime;
import com.gmunidata.schedule.service.ICourseTimeService;
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

import java.util.List;
import java.util.Map;

@Api(value = "/courseTime",description = "开课时间管理控制类")
@RestController
@RequestMapping(value = "courseTime")
public class CourseTimeController {

    @Autowired
    @Qualifier("courseTimeServiceImp")
    ICourseTimeService courseTimeServiceImp;

    @ApiOperation(value = "新增开课时间管理信息")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Content add(@RequestBody CourseTime courseCfg){
        return  courseTimeServiceImp.addCourseTime(courseCfg);
    }


    @ApiOperation(value = "编辑开课时间信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Content update(@RequestBody CourseTime courseCfg){
        return  courseTimeServiceImp.updateCourseTime(courseCfg);
    }

    @ApiOperation(value = "删除开课时间信息")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ApiImplicitParam(name = "couIds",value = "所要删除的id",required = true)
    public Content del(@RequestBody @ApiIgnore Map<String,String> params){
        return  courseTimeServiceImp.delCourseTime(params.get("couIds"));
    }

    @ApiOperation(value = "查询开课时间信息")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade",value = "查询条件：年级"),
            @ApiImplicitParam(name = "education",value = "查询条件：学历"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<CourseTime> list(@RequestBody @ApiIgnore Map<String,String> params){
        return  new PageInfo<>(courseTimeServiceImp.listCourseTime(params));
    }

    @ApiOperation(value = "按年级查询开课时间信息无分页调用的方法")
    @RequestMapping(value = "/getCourseTime",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade",value = "查询条件：年级")})
    public List<CourseTime> getCourseTime(@RequestBody @ApiIgnore Map<String,String> params){
        return  courseTimeServiceImp.getCourseTime(params);
    }

}
