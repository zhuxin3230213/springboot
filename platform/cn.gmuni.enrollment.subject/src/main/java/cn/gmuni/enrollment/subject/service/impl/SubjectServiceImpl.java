package cn.gmuni.enrollment.subject.service.impl;

import cn.gmuni.enrollment.guide.mapper.InfoContentMapper;
import cn.gmuni.enrollment.guide.model.ClickThrough;
import cn.gmuni.enrollment.subject.mapper.SubjectMapper;
import cn.gmuni.enrollment.subject.model.ProContent;
import cn.gmuni.enrollment.subject.model.Subject;
import cn.gmuni.enrollment.subject.service.ISubjectService;
import cn.gmuni.maintenance.model.InfoContent;
import cn.gmuni.org.model.Department;
import cn.gmuni.subscribe.subject.SubjectManager;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SubjectServiceImpl implements ISubjectService {
    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    InfoContentMapper infoContentMapper;

    private final static String REMOVE_SUBJECT = "REMOVE_SUBJECT";

    //注册订阅者
    @PostConstruct
    public void init(){
        SubjectManager.create(REMOVE_SUBJECT);
    }

    @Override
    public Object syncSubjects() {
        Map<String, Object> map = new HashMap<>();
        //查询出所有新增的学院学科信息
        List<Department> departments = subjectMapper.selectUnSubjetedDept();
        //查询出所有删除的学科信息的ID
        List<Map<String, Object>> deleteIds = subjectMapper.selectAllDeleteSubIds();
        List<String> dIds = new ArrayList<>();
        for (Map<String, Object> entry : deleteIds) {
            dIds.add((String) entry.get("id"));
        }
        int deleted = 0;
        if (dIds.size() > 0) {
            //删除学科信息
             deleted = subjectMapper.deleteSubjectByIds(dIds);
            //通知所有的订阅者
               SubjectManager.get(REMOVE_SUBJECT).notifyObserver(dIds);
                //删除学科信息对应的学科介绍与中间表数据
                subjectMapper.deleteArticleByIds(dIds);
                subjectMapper.deleteMiddleByIds(dIds);
        }
        map.put("deleted", deleted);

        //处理所有新增的学院学科信息
        List<Subject> subjects = new ArrayList<>();
        for (int i = 0; i < departments.size(); i++) {
            Department dept = departments.get(i);
            Subject sub = new Subject();
            sub.setId(IdGenerator.getId());
            sub.setDeptId(dept.getId());
            sub.setDept(dept);
            sub.setSort(dept.getSort());
            if ("xk".equals(dept.getFaculty())) {
                sub.setStatus("1");
                sub.setType("wk");
            }
            subjects.add(sub);
        }
        //添加新增的信息
        if (subjects.size() > 0) {
            subjectMapper.insertSubjects(subjects);
            //处理学院名称的显示问题
            List<Map<String, Object>> maps = listAllSubject();
            for (int i = 0; i < subjects.size(); i++) {
                Subject subject = subjects.get(i);
                point:
                for (int j = 0; j < maps.size(); j++) {
                    Map<String, Object> academy = maps.get(j);
                    List<Map> children = (List<Map>) academy.get("children");
                    for (int k = 0; k < children.size(); k++) {
                        Map child = children.get(k);
                        if (child.get("id").equals(subject.getId())) {
                            subject.setAcademyName(academy.get("name").toString());
                            break point;
                        }
                    }
                }
            }
            //处理新增的学科专业的正文信息
            for (Subject sub : subjects) {
                //if (null != sub.getStatus()) {
                String articleId = IdGenerator.getId();
                ProContent p = new ProContent();
                p.setId(IdGenerator.getId());
                p.setArticleId(articleId);
                p.setProId(sub.getId());

                    subjectMapper.insertProContentIds(p);

                InfoContent content = new InfoContent();
                content.setId(articleId);
                content.setTitle(sub.getDept().getName());
                content.setCreateTime(new Date());
                content.setContent("");
                content.setSource("");
                content.setParentCode("2004");
                content.setKeywords("");
                content.setCover(null);
                content.setUpdateTime(new Date());
                content.setSort(0);
                content.setDescription("");
                int i = 0;

                    i = infoContentMapper.saveInfo(content);
                   if (i > 0) {
                       ClickThrough ct = new ClickThrough();
                       ct.setId(IdGenerator.getId());
                       ct.setArticleId(articleId);
                       ct.setClickThrough(0);
                       ct.setType("0");
                       infoContentMapper.saveClickThrough(ct);
                        }

                sub.setArticle(content);
                //}
            }
        }
        map.put("added", subjects);
        return map;
    }

    @Override
    public Map<String, Object> saveSubjectInfos(List<Subject> subjects) {
        Map<String, Object> res = new HashMap<>();
        int count = 0;
        if (subjects.size() > 0) {
            count = subjectMapper.updateSubjects(subjects);
            for (Subject sub : subjects) {
                InfoContent content = sub.getArticle();
                if (content != null) {
                    content.setUpdateTime(new Date());
                    try{
                        infoContentMapper.updateInfo(content);
                    }catch (Exception e){

                    }
                }
            }
        }
        res.put("flag", count > 0 ? true : false);
        return res;
    }


    @Override
    public List<Map<String, Object>> listAllSubject() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> maps = subjectMapper.listAllSubject();
        for (int i = 0; i < maps.size(); i++) {
            Map map = new TreeMap(maps.get(i));
            if (map.get("faculty").equals("xy")) {
                List<Map> children = new ArrayList<>();
                for (int j = 0; j < maps.size(); j++) {
                    Map m = maps.get(j);
                    if (!m.get("dlevel_code").equals(map.get("dlevel_code")) && m.get("dlevel_code").toString().startsWith(map.get("dlevel_code").toString())) {
                        children.add(m);
                    }
                }
                map.put("children", children);
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public InfoContent getArticleBySubId(String id) {
        return subjectMapper.getArticleBySubId(id);
    }


}
