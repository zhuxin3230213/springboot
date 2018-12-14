package cn.gmuni.enrollment.subject.service;

import cn.gmuni.enrollment.subject.model.Subject;
import cn.gmuni.maintenance.model.InfoContent;

import java.util.List;
import java.util.Map;

public interface ISubjectService {
     Object syncSubjects();
     Map<String, Object> saveSubjectInfos(List<Subject> subjects);

     List<Map<String, Object>> listAllSubject();

     InfoContent getArticleBySubId(String id);
}
