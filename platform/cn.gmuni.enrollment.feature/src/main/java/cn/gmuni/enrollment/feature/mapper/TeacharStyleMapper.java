package cn.gmuni.enrollment.feature.mapper;

import cn.gmuni.enrollment.feature.model.TeacherStyle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TeacharStyleMapper {
    List<TeacherStyle> list(Map<String,String> params);
    int save(TeacherStyle ts);
    int update(TeacherStyle ts);
    int delete(List<String> list);
    int checkByCodeEqual(String code);
}
