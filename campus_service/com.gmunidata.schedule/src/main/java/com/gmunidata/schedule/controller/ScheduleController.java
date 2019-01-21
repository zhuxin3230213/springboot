package com.gmunidata.schedule.controller;

import com.gmunidata.base.response.BaseResponse;
import com.gmunidata.base.response.Content;
import com.gmunidata.schedule.model.Schedule;
import com.gmunidata.schedule.service.IScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(value = "/schedule",description = "课程管理控制类")
@RestController
@RequestMapping(value = "schedule")
public class ScheduleController {

    @Autowired
    @Qualifier("scheduleServiceImp")
    IScheduleService scheduleServiceImp;

    @ApiOperation(value = "课程管理（新增，修改，删除）")
    @RequestMapping(value = "addOrUpdateOrDel",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sunday",value = "星期几（周几的课程）",required = true),
            @ApiImplicitParam(name = "targetNc",value = "目标节数（点的是那一节）",required = true),
            @ApiImplicitParam(name = "nc",value = "节数",required = true),
            @ApiImplicitParam(name = "courseTimeId",value = "学期信息id",required = true),
            @ApiImplicitParam(name = "classCode",value = "班级编码",required = true),
            @ApiImplicitParam(name = "sch",value = "课程信息",required = true)})
    public Content addOrUpdateOrDel(@RequestBody @ApiIgnore Map<String,Object> params){
          params.put("check",1);
        return scheduleServiceImp.addSchedule(params);
    }


    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classCode",value = "班级编码",required = true),
            @ApiImplicitParam(name = "courseTimeId",value = "学年学期id",required = true),
            @ApiImplicitParam(name = "week",value = "第几周")})
    public List<Schedule> list(@RequestBody @ApiIgnore Map<String,String> params){
        return scheduleServiceImp.listSchedule(params);
    }


    @RequestMapping(value = "outSchedule",method = RequestMethod.POST)
    @ApiOperation(value = "导出课程表 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classCode",value = "班级编码",required = true),
            @ApiImplicitParam(name = "courseTimeId",value = "学年学期id",required = true)})
    public  void  outSchedule(@RequestBody @ApiIgnore Map<String,String> params,HttpServletResponse response){
         scheduleServiceImp.outSchedule(params,response);
    }

    @RequestMapping(value = "modelSchedule",method = RequestMethod.POST)
    @ApiOperation(value = "导出课程表模板 ")
    public  void  modelSchedule(HttpServletResponse response){
        scheduleServiceImp.modelSchedule(response);

    }

    @RequestMapping(value = "inSchedule",method = RequestMethod.POST)
    @ApiOperation(value = "导入课程表 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "uuid",required = true),
            @ApiImplicitParam(name = "className",value = "班级名称",required = true),
            @ApiImplicitParam(name = "courseTimeId",value = "开课时间管理id",required = true),
            @ApiImplicitParam(name = "name",value = "sheet页名称",required = true)
    })
    public  Content  inSchedule(@RequestBody @ApiIgnore Map<String,Object> params){
           return  scheduleServiceImp.inSchedule(params);

    }
    @RequestMapping(value = "getAppSchedule",method = RequestMethod.POST)
    @ApiOperation(value = "app端获取课程表 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "学生学号",required = true),
            @ApiImplicitParam(name = "semester",value = "学期数",required = true),
            @ApiImplicitParam(name = "academicYear",value = "学年  数据格式：2012-2013",required = true),
            @ApiImplicitParam(name = "week",value = "周数",required = true)
    })
    public Map<String, Object> getAppSchedule(@RequestBody @ApiIgnore Map<String,String> params){
        return scheduleServiceImp.getAppSchedule(params);
    }
    @RequestMapping(value = "getScheduleInfo",method = RequestMethod.POST)
    @ApiOperation(value = "app端获取学期上课信息列表 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "学生学号",required = true)
    })
    public List<Map<String, String>> getScheduleInfo(@RequestBody @ApiIgnore Map<String,String> params){
        return scheduleServiceImp.getScheduleInfo(params);
    }
    @RequestMapping(value = "getScheduleWeek",method = RequestMethod.POST)
    @ApiOperation(value = "app端获取课程上课周数信息 ")
    public Map<String, String> getScheduleWeek(@RequestBody @ApiIgnore Map<String,String> params){
        return scheduleServiceImp.getScheduleWeek(params);
    }
}
