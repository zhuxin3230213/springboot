package com.gmunidata.student.controller;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.BaseResponse;
import com.gmunidata.base.response.Content;
import com.gmunidata.student.model.Student;
import com.gmunidata.student.model.StudentFamily;
import com.gmunidata.student.model.StudentLocation;
import com.gmunidata.student.model.StudentResume;
import com.gmunidata.student.service.IStudentService;
import com.gmunidata.student.service.StudentLocationService;
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

@Api(value = "/student", description = "学生信息控制类")
@RestController
@RequestMapping(value = "student")
public class StudentController {

    @Autowired
    @Qualifier("studentServiceImp")
    IStudentService studentServiceImp;

    @Autowired
    @Qualifier("studentLocationServiceImpl")
    StudentLocationService studentLocationService;

    @ApiOperation(value = "新增或编辑学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "student", value = "学生信息"),
            @ApiImplicitParam(name = "listFam", value = "家庭信息"),
            @ApiImplicitParam(name = "listRes", value = "教育经历")
    })
    @RequestMapping(value = "/saveOrUp", method = RequestMethod.POST)
    public Content saveOrUp(@RequestBody @ApiIgnore Map<String, Object> params) {
        Map<String, Object> stuMap = (Map<String, Object>) params.get("student");
        List<Map<String, Object>> listFam = (List<Map<String, Object>>) params.get("listFam");
        List<Map<String, Object>> listRes = (List<Map<String, Object>>) params.get("listRes");
        return studentServiceImp.addOrUpStudent(stuMap, listFam, listRes);
    }


    @ApiOperation(value = "删除学生信息")
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stuId", value = "删除学生的id", required = true)})
    public Content del(@RequestBody @ApiIgnore Map<String, String> params) {
        return studentServiceImp.delStudent(params.get("stuId"));
    }

    @ApiOperation(value = "查询学生信息")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "查询条件 姓名"),
            @ApiImplicitParam(name = "code", value = "查询条件 编码"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<Student> list(@RequestBody @ApiIgnore Map<String, String> params) {
        return new PageInfo<>(studentServiceImp.listStudent(params));
    }

    @ApiOperation(value = "查询学生家庭信息")
    @RequestMapping(value = "/listF", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件 学生id", required = true)})
    public PageInfo<StudentFamily> listF(@RequestBody @ApiIgnore Map<String, String> params) {
        return new PageInfo<>(studentServiceImp.listFam(params));
    }

    @ApiOperation(value = "查询学生教育经历")
    @RequestMapping(value = "/listR", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询条件 学生id", required = true)})
    public PageInfo<StudentResume> listR(@RequestBody @ApiIgnore Map<String, String> params) {
        return new PageInfo<>(studentServiceImp.listRes(params));
    }


    @RequestMapping(value = "outStudent", method = RequestMethod.POST)
    @ApiOperation(value = "导出学生表 ")
    public void outStudent(HttpServletResponse response) {
        studentServiceImp.outStudent(response);
    }

    @RequestMapping(value = "modelStudent", method = RequestMethod.POST)
    @ApiOperation(value = "导出学生信息模板 ")
    public void modelStudent(HttpServletResponse response) {
        studentServiceImp.modelStudent(response);
    }

    @RequestMapping(value = "inStudent", method = RequestMethod.POST)
    @ApiOperation(value = "导入学生表 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true),
            @ApiImplicitParam(name = "merge", value = "是否覆盖重复数据（0:否 1：是）", required = true),
            @ApiImplicitParam(name = "name", value = "sheet页名称", required = true),
            @ApiImplicitParam(name = "addOrCover", value = "追加或者覆盖", required = true),
            @ApiImplicitParam(name = "startLine", value = "起始行", required = true),
            @ApiImplicitParam(name = "startClu", value = "起始列", required = true)
    })

    public Content inStudent(@RequestBody @ApiIgnore Map<String, Object> params) {
        return studentServiceImp.inStudent(params);

    }


    @RequestMapping(value = "updatePwdByCode", method = RequestMethod.POST)
    @ApiOperation(value = "根据学生编码修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "学生学号", required = true)
    })
    public Content updatePwdByCode(@RequestBody String code) {
        return studentServiceImp.updatePwdByCode(code);
    }


    //app添加学生定位信息
    @RequestMapping(value = "/stuBehavior")
    @ApiOperation(value = "添加学生位置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "userInfo", name = "用户"),
            @ApiImplicitParam(value = "school", name = "学校"),
            @ApiImplicitParam(value = "studentId", name = "学号", required = true),
            @ApiImplicitParam(value = "gprsTime", name = "定位时间"),
            @ApiImplicitParam(value = "longitude", name = "经度", required = true),
            @ApiImplicitParam(value = "latitude", name = "纬度", required = true),
            @ApiImplicitParam(value = "altitude", name = "海拔"),
            @ApiImplicitParam(value = "city", name = "城市"),
            @ApiImplicitParam(value = "address", name = "位置信息"),
            @ApiImplicitParam(value = "stuSignTime", name = "学生签到时间")
    })
    public Map<String, Object> stuBehavior(@RequestParam @ApiIgnore Map<String, String> params) {
        return studentLocationService.add(params);
    }


    @RequestMapping("/stuBehaviorList")
    @ApiOperation(value = "app学生位置信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "currentPage", name = "当前页", required = true),
            @ApiImplicitParam(value = "pageSize", name = "条目数", required = true),
            @ApiImplicitParam(value = "userInfo", name = "用户", required = true),
            @ApiImplicitParam(value = "school", name = "学校", required = true),
            @ApiImplicitParam(value = "studentId", name = "学号", required = true),

    })
    public List<Map<String, Object>> stuBehaviorList(@RequestParam @ApiIgnore Map<String, String> params) {
        return studentLocationService.list(params);
    }


    @PostMapping("/addressList")
    @ApiOperation(value = "定位信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "studentCode", name = "学号", required = true)
    })
    public PageInfo<Map<String, Object>> addressList(@RequestBody Map<String, Object> params) {
        return new PageInfo<>(studentLocationService.addressList(params));
    }

}
