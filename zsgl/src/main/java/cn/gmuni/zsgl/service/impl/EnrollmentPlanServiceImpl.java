package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.EnrollmentPlanRepository;
import cn.gmuni.zsgl.service.EnrollmentPlanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


/**
 * @Author:ZhuXin
 * @Description: 招生计划业务类
 * @Date:Create in 16:02 2018/6/4
 * @Modified By:
 **/
@Transactional
@Service
public class EnrollmentPlanServiceImpl implements EnrollmentPlanService {


    @Autowired
    EnrollmentPlanRepository enrollmentPlanRepository;


    /**
     * 根据id查询招生计划
     *
     * @param epId
     * @return
     */
    @Override
    public Map getEnPlanById(String epId) {
        if (!StringUtils.isEmpty(epId)) {
            return enrollmentPlanRepository.getById(epId);
        } else {
            return null;
        }
    }

    /**
     * 获取招生计划列表
     *
     * @return
     */
    @Override
    public List<Map> listEnPlans() {
        return enrollmentPlanRepository.listEnrollmentPlans();
    }

    /**
     * 根据年份查询招生计划
     *
     * @param year
     * @return
     */
    @Override
    public List<Map> listEnPlanByYear(Integer year) {
        if (year != null) {
            return enrollmentPlanRepository.listEnrollmentPlansByYear(year);
        } else {
            return null;
        }
    }

    /**
     * 根据省份查询招生计划
     *
     * @param provinces
     * @return
     */
    @Override
    public List<Map> listEnPlanByProvinces(String provinces) {
        if (!StringUtils.isEmpty(provinces)) {
            return enrollmentPlanRepository.listEnrollmentPlansByProvinces(provinces);
        } else {
            return null;
        }
    }

    /**
     * 根据年份和省份查询招生计划
     *
     * @param year
     * @param provinces
     * @return
     */
    @Override
    public List<Map> listEnPlansByYearAndProvinces(Integer year, String provinces) {
        if (year != null && !StringUtils.isEmpty(provinces)) {
            return enrollmentPlanRepository.listEnrollmentPlansByYearAndProvinces(year, provinces);
        } else if (year == null && !StringUtils.isEmpty(provinces)) {
            return enrollmentPlanRepository.listEnrollmentPlansByProvinces(provinces);
        } else if (year != null && StringUtils.isEmpty(provinces)) {
            return enrollmentPlanRepository.listEnrollmentPlansByYear(year);
        } else {
            return null;
        }
    }

    /**
     * 获取省份列表
     *
     * @return
     */
    @Override
    public List<Map> listProvinces() {
        return enrollmentPlanRepository.listProvinces();
    }
}
