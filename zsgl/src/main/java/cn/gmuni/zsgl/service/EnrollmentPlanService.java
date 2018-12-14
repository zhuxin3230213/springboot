package cn.gmuni.zsgl.service;


import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 招生计划业务接口
 * @Date:Create in 16:01 2018/6/4
 * @Modified By:
 **/


public interface EnrollmentPlanService {


    /**
     * 根据id查询招生计划
     *
     * @param epId
     * @return
     */
    Map getEnPlanById(String epId);

    /**
     * 获取招生计划列表
     *
     * @return
     */
    List<Map> listEnPlans();

    /**
     * 根据年份获取招生计划
     *
     * @param year
     * @return
     */
    List<Map> listEnPlanByYear(Integer year);

    /**
     * 根据省份查询招生计划
     *
     * @param provinces
     * @return
     */
    List<Map> listEnPlanByProvinces(String provinces);

    /**
     * 根据年份和省份查询招生计划
     *
     * @param year
     * @param provinces
     * @return
     */
    List<Map> listEnPlansByYearAndProvinces(Integer year, String provinces);

    /**
     * 获取省份列表
     *
     * @return
     */
    List<Map> listProvinces();

}
