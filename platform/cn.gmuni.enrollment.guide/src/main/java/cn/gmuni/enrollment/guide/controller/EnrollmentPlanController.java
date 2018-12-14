package cn.gmuni.enrollment.guide.controller;

import cn.gmuni.enrollment.guide.model.EnrollmentPlan;
import cn.gmuni.enrollment.guide.service.IEnrollmentPlanService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.management.ValueExp;
import java.util.List;
import java.util.Map;

/**
 * 招生计划控制类
 */
@Api(value = "/enrollmentPlan", description = "招生计划控制类")
@RestController
@RequestMapping(value = "enrollmentPlan")
public class EnrollmentPlanController {
    @Autowired
    @Qualifier("enrollmentPlanServiceImpl")
    IEnrollmentPlanService planService;

    /**
     * 获取所有招生计划
     *
     * @return
     */
    @ApiOperation(value = "获取所有招生计划")
    @RequestMapping(value = "getAllEnrollmentPlan", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province", value = "省份名称"),
            @ApiImplicitParam(name = "year", value = "年份四位数字"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @ResponseBody
    public PageInfo<Map<String,Object>> getAllEnrollmentPlan(@RequestBody @ApiIgnore Map<String, String> params) {
        return planService.getAllEnrollmentPlan(params);
    }
    /**
     * 校验招生计划信息
     *
     * @return
     */
    @ApiOperation(value = "校验招生计划信息")
    @RequestMapping(value = "checkPlanInfo", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province", value = "省份名称"),
            @ApiImplicitParam(name = "year", value = "年份四位数字"),
            @ApiImplicitParam(name = "academy", value = "学院")})
    @ResponseBody
    public Map<String, Object> checkPlanInfo(@RequestBody @ApiIgnore Map<String, String> params) {
        return planService.checkPlanInfo(params);
    }

    @ApiOperation(value = "通过年份省份学院查询下面的专业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province", value = "省份名称"),
            @ApiImplicitParam(name = "year", value = "年份四位数字"),
            @ApiImplicitParam(name = "academy", value = "学院")})
    @ResponseBody
    @PostMapping(value="listPlanByproYearAca")
    public List<Map<String, Object>> listPlanByproYearAca(@RequestBody @ApiIgnore Map<String, Object> params){
        return planService.listPlanByproYearAca(params);
    }
    /**
     * 删除计划信息
     *
     * @return
     */
    @ApiOperation(value = "删除计划信息")
    @RequestMapping(value = "deletePlans", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planIds", value = "逗号分割的id数组")})
    @ResponseBody
    public Map<String, Object> deletePlans(@RequestBody @ApiIgnore Map<String, String> params) {
        return planService.deletePlans(params);
    }
    /**
     * 获取省份列表
     *
     * @return
     */
    @ApiOperation(value = "获取省份列表")
    @RequestMapping(value = "listAreas", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> listAreas() {
        return planService.listAreas();
    }
    /**
     * 列表显示学院信息
     *
     * @return
     */
    @ApiOperation(value = "列表显示学院信息")
    @RequestMapping(value = "listAcademies", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> listAcademies() {
        return planService.listAcademies();
    }
    /**
     * 根据学院获取专业信息
     *
     * @return
     */
    @ApiOperation(value = "根据学院获取专业信息")
    @RequestMapping(value = "listSubjectByAcademy", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "academyId", value = "学院id")})
    @ResponseBody
    public List<Map<String, Object>> listSubjectByAcademy(@RequestBody @ApiIgnore Map<String, String> params) {
        return planService.listSubjectByAcademy(params);
    }
    /**
     * 保存或者更新招生计划信息列表
     *
     * @return
     */
    @ApiOperation(value = "保存或者更新招生计划信息列表")
    @RequestMapping(value = "savePlans", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> savePlans(@RequestBody List<EnrollmentPlan> plans) {
        return planService.savePlans(plans);
    }


    @ApiOperation(value = "修改招生计划")
    @ResponseBody
    @PostMapping(value="editPlan")
    public EnrollmentPlan editPlan(@RequestBody EnrollmentPlan plan){
        return planService.edit(plan);
    }


}
