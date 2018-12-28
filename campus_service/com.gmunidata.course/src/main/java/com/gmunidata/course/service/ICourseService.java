package com.gmunidata.course.service;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.course.model.Course;

import java.util.Map;

public interface ICourseService {

    /**
     * 添加班级
     * @param course
     * @return
     */
    public Content addCourse(Course course);

    /**
     * 编辑班级信息
     * @param course
     * @return
     */
    public Content updateCourse(Course course);

    /**
     * 删除班级
     * @param cids
     * @return
     */
    public Content delCourse(String cids);


    /**
     * 查询
     * @param params
     * @return
     */
    public PageInfo<Course> listCouser(Map<String, String> params);



}
