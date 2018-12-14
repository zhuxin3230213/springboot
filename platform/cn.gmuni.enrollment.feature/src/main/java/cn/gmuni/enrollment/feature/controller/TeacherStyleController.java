package cn.gmuni.enrollment.feature.controller;

import cn.gmuni.enrollment.feature.model.TeacherStyle;
import cn.gmuni.enrollment.feature.service.ITeacherStyleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 名师风采
 */
@Api(value = "名师风采")
@RestController
@RequestMapping("teacherstyle")
public class TeacherStyleController {

    @Autowired
    @Qualifier(value = "teacherStyleServiceImpl")
    private ITeacherStyleService teacherStyleService;

    @ResponseBody
    @PostMapping("/list")
    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "facultyCode", value = "对应院系编码"),
            @ApiImplicitParam(name = "subjectCode", value = "对应学科编码"),
            @ApiImplicitParam(name = "name",value = "对应教师名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<TeacherStyle> list(@RequestBody @ApiIgnore Map<String, String> params) {
        return teacherStyleService.list(params);
    }

    @ResponseBody
    @PostMapping("/save")
    @ApiOperation(value = "添加数据")
    public TeacherStyle save(@RequestBody TeacherStyle ts) {
        return teacherStyleService.save(ts);
    }

    @ResponseBody
    @PostMapping("/update")
    @ApiOperation(value = "更新数据")
    public TeacherStyle update(@RequestBody TeacherStyle ts) {
        return teacherStyleService.update(ts);
    }

    @ResponseBody
    @PostMapping("/delete")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "逗号分割的id数组")})
    public Map<String, Object> delete(@RequestBody @ApiIgnore Map<String, String> params) {
        return teacherStyleService.delete(params);
    }
    /**
     * 检查code是否存在
     * @param params
     * @return
     */
    @ApiOperation(value = "检查code是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true)})
    @RequestMapping(value = "checkCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkCode(@RequestBody @ApiIgnore Map<String, String> params){
        return teacherStyleService.checkCode(params);
    }
}
