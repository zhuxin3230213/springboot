package com.gmunidata.newsNotice.mapper;

import com.gmunidata.newsNotice.model.GroupPeople;
import com.gmunidata.newsNotice.model.InfoGroup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface InfoGroupMapper {

    //获取学院/专业列表
    List<Map<String,Object>> getDeptAndSubject(List<String> list);

    //获取学院/专业列表(超级用户)
    List<Map<String,Object>> getAllDeptAndSubject();

    //获取班级列表
    List<Map<String,Object>> getAllClass();

    //获取组
    List<Map<String,Object>> selectGroup(Map<String,Object> params);

    //从学生列表获取组的成员
    List<Map<String,Object>> getGroupStudentByStudent(Map<String,Object> params);

    //从组中获取组的成员
    List<Map<String,Object>> getGroupStudentByGroup(Map<String,Object> params);

    //添加新的组
    int addInfoGroup(InfoGroup infoGroup);

    //添加组成员
    int addGroupPeople(List<GroupPeople> list);

    //删除组成员
    int deleteGroupPeople(Map<String,Object> params);

    //校验创建组的编码重复
    int checkCode(InfoGroup infoGroup);

    //删除组
    int deleteGroup(Map<String,Object> params);

    //删除组中学生
    int deletePeopleByGroup(Map<String,Object> params);

    //查询人员所属部门列表（所属部门）
    List<String> getDeptName(String userName);

    //查询人员所属部门列表 (兼职部门)
    List<String> getDeptPartName(String userName);

    //获取部门下所有的班级
    List<Map<String,Object>> getClass(List<String> ids);

 }
