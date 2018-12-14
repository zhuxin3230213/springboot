package cn.gmuni.enrollment.guide.mapper;

import cn.gmuni.enrollment.guide.model.EnrollmentPlan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EnrollmentPlanMapper {
    int insert(EnrollmentPlan record);

    int updateByPrimaryKey(EnrollmentPlan record);

    List<Map<String, Object>> listAreas();

    List<Map<String, Object>> listAcademies();

    List<Map<String, Object>> listSubjectByAcademy(String deptId);

    int deletePlans(List<String> list);

    List<Map<String, Object>> getAllEnrollmentPlan(Map<String, Object> params);

    int checkPlanInfo(Map<String, String> params);

    int savePlans(List<EnrollmentPlan> list);

    int updatePlans(List<EnrollmentPlan> list);

    int edit(EnrollmentPlan plan);
}