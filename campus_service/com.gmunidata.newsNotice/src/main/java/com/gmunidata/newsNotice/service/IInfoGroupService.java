package com.gmunidata.newsNotice.service;

import com.gmunidata.base.response.Content;
import com.gmunidata.newsNotice.model.GroupPeople;
import com.gmunidata.newsNotice.model.InfoGroup;

import java.util.List;
import java.util.Map;

public interface IInfoGroupService {

    //获取学院专业班级树
    List<Map<String,Object>> getClassTree(String userName);

    //获取组
    List<Map<String,Object>> selectGroup(Map<String,Object> params);

    //获取组的成员
    List<Map<String,Object>> getGroupStudent(Map<String,Object> params);

    //添加新的组
    Content addInfoGroup(InfoGroup infoGroup);

    //修改组成员
    Content updateGroupPeople(Map<String,Object> params);

    //删除组
    Content delGroup(Map<String,Object> params);

}
