package cn.gmuni.enrollment.guide.service;

import cn.gmuni.enrollment.guide.model.EnrollmentPlan;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

public interface IEnrollmentPlanService {
    /**
     * 获取所有招生计划
     *
     * @return
     */
    public PageInfo<Map<String,Object>> getAllEnrollmentPlan(@RequestBody @ApiIgnore Map<String, String> params);
    /**
     * 校验招生计划信息
     *
     * @return
     */
    public Map<String, Object> checkPlanInfo(@RequestBody @ApiIgnore Map<String, String> params);
    /**
     * 删除计划信息
     *
     * @return
     */
    public Map<String, Object> deletePlans(@RequestBody @ApiIgnore Map<String, String> params);
    /**
     * 获取省份列表
     *
     * @return
     */
    public List<Map<String, Object>> listAreas();
    /**
     * 列表显示学院信息
     *
     * @return
     */
    public List<Map<String, Object>> listAcademies();
    /**
     * 根据学院获取专业信息
     *
     * @return
     */
    public List<Map<String, Object>> listSubjectByAcademy(@RequestBody @ApiIgnore Map<String, String> params) ;
    /**
     * 保存或者更新招生计划信息列表
     *
     * @return
     */
    public Map<String, Object> savePlans(@RequestBody List<EnrollmentPlan> plans);

    List<Map<String, Object>> listPlanByproYearAca(Map<String, Object> params);

    EnrollmentPlan edit(EnrollmentPlan plan);
}
