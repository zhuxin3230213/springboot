package cn.gmuni.sc.college.service;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.college.model.College;

import java.util.List;
import java.util.Map;

public interface ICollegeService {
    List<College> listAll();

    //新增学校
    Content addSchool(College college);

    //修改学校信息
    Content updateSchool(College college);

    //删除学校信息
    Content deslSchool(List<String> ids);

    //查询学校信息
    List<College> listSchool(Map<String,Object> params);

    //查询所有学校信息
    List<Map<String,String>> getSchool();



}
