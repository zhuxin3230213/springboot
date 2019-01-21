package cn.gmuni.sc.log.controller;

import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.model.OperatorLog;
import cn.gmuni.sc.log.service.ILogService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/log",description = "日志管理控制类")
@RestController
@RequestMapping("/log")
public class ScLogController {

    @Autowired
    @Qualifier("logServiceImp")
    ILogService logServiceImp;

    @ApiOperation(value = "查询操作日志信息")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module",value = "日志所属模块"),
            @ApiImplicitParam(name = "type",value = "日志类型"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间"),
            @ApiImplicitParam(name = "schoolCode",value = "学校编码"),
            @ApiImplicitParam(name = "userName",value = "用户名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<OperatorLog> list(@RequestBody @ApiIgnore Map<String,Object> params){
        return new PageInfo<>(logServiceImp.listLog(params));
    }


    @ApiOperation(value = "查询操作日志详细信息")
    @RequestMapping(value = "/getMessage",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "所要查询的日志的id",required = true)})
    public Map<String,String> getMessage(@RequestBody @ApiIgnore Map<String,String> params){
        return logServiceImp.getMessage(params.get("id"));
    }

    @ApiOperation(value = "查询模块列表")
    @RequestMapping(value = "/listModule",method = RequestMethod.POST)
    public List<Map<String,String>> getMessage(){
        SysLogModule[] values = SysLogModule.values();
        List<Map<String,String>> list = new ArrayList<>();
        for(SysLogModule m : values){
            Map<String,String> map = new HashMap<>();
            map.put("name",m.getName());
            map.put("desc",m.getDesc());
            list.add(map);
        }
        return list;
    }

    @ApiOperation(value = "查询日访问人数")
    @RequestMapping(value = "/getDayPeopleNum",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "data",value = "所要查询的日期类型",required = true)})
    public List<Map<String,Object>> getDayPeopleNum(@RequestBody @ApiIgnore Map<String,Object> params){
        return  logServiceImp.getDayPeopleNum(params);
    }

    @ApiOperation(value = "查询各模块或日志类型的访问人数")
    @RequestMapping(value = "/getModuleOrTypePeopleNum",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "type",value = "所要查询功能是模块还是日志类型",required = true),
            @ApiImplicitParam(name = "data",value = "所要查询的日期类型",required = true)})
    public List<Map<String,Object>> getModuleOrTypePeopleNum (@RequestBody @ApiIgnore Map<String,Object> params){
        return  logServiceImp.getModuleOrTypePeopleNum(params);
    }

    @ApiOperation(value = "查询日当天各时间段访问人数")
    @RequestMapping(value = "/getTimePeopleNum",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "data",value = "所要查询的日期") })
    public List<Map<String,Object>> getTimePeopleNum(@RequestBody @ApiIgnore Map<String,Object> params){
        return  logServiceImp.getTimePeopleNum(params);
    }

    @ApiOperation(value = "查询登录日志信息")
    @RequestMapping(value = "/listLogin",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间"),
            @ApiImplicitParam(name = "logType",value = "正常还是异常"),
            @ApiImplicitParam(name = "userName",value = "用户名称"),
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<LoginLog> listLogin(@RequestBody @ApiIgnore Map<String,Object> params){
        return new PageInfo<>(logServiceImp.listLoginLog(params));
    }


    @ApiOperation(value = "查询登录日志详细信息")
    @RequestMapping(value = "/getLoginMessage",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "所要查询的日志的id",required = true)})
    public Map<String,String> getLoginMessage(@RequestBody @ApiIgnore Map<String,String> params){
        return logServiceImp.getLoginMessage(params.get("id"));
    }

    @ApiOperation(value = "登录数量统计页面类型初始化")
    @RequestMapping(value = "/getInitType",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间"),
            @ApiImplicitParam(name = "date",value = "所要查询的日期类型",required = true)})
    public Map<String,Object> getInitType(@RequestBody @ApiIgnore Map<String,Object> params){
        return  logServiceImp.getLoginType(params);
    }

    @ApiOperation(value = "登录数量统计页面新老用户登录初始化")
    @RequestMapping(value = "/getInitPeopleNum",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "所要查询的学校编码"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "终止时间"),
            @ApiImplicitParam(name = "date",value = "所要查询的日期类型",required = true)})
    public List<Map<String,Object>> getInitPeopleNum(@RequestBody @ApiIgnore Map<String,Object> params){
        return  logServiceImp.getUserNum(params);
    }

}
