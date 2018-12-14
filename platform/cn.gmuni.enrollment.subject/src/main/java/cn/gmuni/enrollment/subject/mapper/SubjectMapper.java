package cn.gmuni.enrollment.subject.mapper;

import cn.gmuni.enrollment.subject.model.ProContent;
import cn.gmuni.enrollment.subject.model.Subject;
import cn.gmuni.maintenance.model.InfoContent;
import cn.gmuni.org.model.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SubjectMapper {
    /**
     * 查询所有的新增的学院学科
     * @return
     */
    List<Department> selectUnSubjetedDept();
    List<Map<String, Object>> listAllSubject();

    /**
     * 根据已删除的部门删除学科信息
     * @return
     */
    int deleteSubjectByIds(List<String> ids);
    int deleteEnrollmentPlan(List<String> ids);
    int insertSubjects(List<Subject> list);

    int updateSubjects(List<Subject> list);

    List<Map<String,Object>> selectAllDeleteSubIds();

    int deleteArticleByIds(List<String> dids);

    int deleteMiddleByIds(List<String> dids);

    int insertProContentIds(ProContent proContent);

    InfoContent getArticleBySubId(@Param("id") String id);
}
