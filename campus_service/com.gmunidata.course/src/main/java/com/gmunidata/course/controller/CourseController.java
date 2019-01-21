package com.gmunidata.course.controller;


import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.course.model.Course;
import com.gmunidata.course.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api(value = "/course",description = "课程管理控制类")
@RestController
@RequestMapping(value = "course")
public class CourseController {

    @Autowired
    @Qualifier("courseServiceImp")
    ICourseService courseServiceImp;

    @ApiOperation(value = "新增科目")
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Content add(@RequestBody Course course){
        return courseServiceImp.addCourse(course);
    }

    @ApiOperation(value = "编辑科目")
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Content update(@RequestBody Course course){
        return courseServiceImp.updateCourse(course);
    }

    @ApiOperation(value = "删除科目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的班级的id", required = true)})
    @RequestMapping(value = "del",method = RequestMethod.POST)
    public Content del(@RequestBody  @ApiIgnore Map<String,String> params){
        return courseServiceImp.delCourse(params.get("delId"));
    }

    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询科目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "查询条件 名称"),
            @ApiImplicitParam(name = "code", value = "查询条件 编码"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<Course> list(@RequestBody @ApiIgnore Map<String,String> params){
        return courseServiceImp.listCouser(params);
    }



}
