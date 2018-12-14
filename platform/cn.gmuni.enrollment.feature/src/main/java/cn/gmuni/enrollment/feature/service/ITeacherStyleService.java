package cn.gmuni.enrollment.feature.service;

import cn.gmuni.enrollment.feature.model.TeacherStyle;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface ITeacherStyleService {
    PageInfo<TeacherStyle> list(Map<String,String> params);
    TeacherStyle save(TeacherStyle ts);
    TeacherStyle update(TeacherStyle ts);
    Map<String, Object> delete(Map<String, String> params);
    Map<String, Object> checkCode(Map<String, String> params);
}
