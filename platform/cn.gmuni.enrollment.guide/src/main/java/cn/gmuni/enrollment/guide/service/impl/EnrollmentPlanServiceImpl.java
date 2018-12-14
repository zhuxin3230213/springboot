package cn.gmuni.enrollment.guide.service.impl;

import cn.gmuni.enrollment.guide.mapper.EnrollmentPlanMapper;
import cn.gmuni.enrollment.guide.model.EnrollmentPlan;
import cn.gmuni.enrollment.guide.service.IEnrollmentPlanService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class EnrollmentPlanServiceImpl implements IEnrollmentPlanService {
    @Autowired
    EnrollmentPlanMapper mapper;

    /**
     * 获取所有招生计划
     *
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> getAllEnrollmentPlan(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("year", params.get("year"));
        queryPara.put("province", params.get("province"));
        PageHelper.startPage(page.getPage(), page.getSize());
        return new PageInfo<Map<String, Object>>(mapper.getAllEnrollmentPlan(queryPara));
    }

    /**
     * 校验招生计划信息
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> checkPlanInfo(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        int num = mapper.checkPlanInfo(params);
        if (num > 0) {
            res.put("flag", true);
        } else {
            res.put("flag", false);
        }
        return res;
    }

    /**
     * 删除计划信息
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> deletePlans(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        boolean flag = false;
        String planIds = params.get("planIds");
        List<String> idList = Arrays.asList(planIds.split(","));
        if (idList.size() > 0) {
            flag = mapper.deletePlans(idList) > 0 ? true : false;
        }
        res.put("flag", flag);
        return res;
    }

    /**
     * 获取省份列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listAreas() {
        return mapper.listAreas();
    }

    /**
     * 列表显示学院信息
     *
     * @return
     */
    public List<Map<String, Object>> listAcademies() {
        return mapper.listAcademies();
    }

    /**
     * 根据学院获取专业信息
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> listSubjectByAcademy(Map<String, String> params) {
        String academyId = params.get("academyId");
        return mapper.listSubjectByAcademy(academyId);
    }

    /**
     * 保存或者更新招生计划信息列表
     *
     * @param plans
     * @return
     */
    @Override
    public Map<String, Object> savePlans(List<EnrollmentPlan> plans) {
        List<EnrollmentPlan> adds = new ArrayList<>();
        List<EnrollmentPlan> updates = new ArrayList<>();
        for (int i = 0; i < plans.size(); i++) {
            EnrollmentPlan plan = plans.get(i);
            if(StringUtils.isEmpty(plan.getId())){
                plan.setId(IdGenerator.getId());
                adds.add(plan);
            }else{
                updates.add(plan);
            }
        }

        Map<String, Object> res = new HashMap<>();
        if(adds.size() > 0){
            mapper.savePlans(adds);
        }
        if(updates.size() > 0){
            mapper.updatePlans(updates);
        }
        res.put("flag", true);
        return res;
    }

    @Override
    public List<Map<String, Object>> listPlanByproYearAca(Map<String, Object> params) {
        return mapper.getAllEnrollmentPlan(params);
    }

    @Override
    public EnrollmentPlan edit(EnrollmentPlan plan) {
        return mapper.edit(plan)>0?plan:null;
    }
}
