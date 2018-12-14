package cn.gmuni.enrollment.guide.objserver;

import cn.gmuni.enrollment.guide.mapper.InfoContentMapper;
import cn.gmuni.subscribe.oserver.IObserver;
import cn.gmuni.subscribe.subject.SubjectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import  java.util.List;

@Component
public class RemoveSubjectObserver implements IObserver<List<String>> {
    @Autowired
    InfoContentMapper mapper;

    @PostConstruct
    public void init(){
        SubjectManager.get("REMOVE_SUBJECT").register(this);
    }

    @Override
    public void update(List<String> ids) {
        //删除招生信息中的数据
        //先删除招生计划
        mapper.deleteEnrollmentPlan(ids);


    }
}
