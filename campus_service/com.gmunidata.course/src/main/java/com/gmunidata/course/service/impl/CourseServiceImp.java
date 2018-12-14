package com.gmunidata.course.service.impl;

import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.course.mapper.CourseMapper;
import com.gmunidata.course.model.Course;
import com.gmunidata.course.service.ICourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImp implements ICourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Content addCourse(Course course) {
       Content con =new Content();
        long num = courseMapper.checkCodeAndNum(course);
        int f=1;
       if (num==0){
           course.setId(IdGenerator.getId());
           course.setCreateTime(new Date());
           f = courseMapper.addCourse(course) == 1 ? 0 : 1;
       }
       con.setFlag(f);
       if(f==0){
           con.setMessage("添加成功！");
           con.setContent(course);
       }else if (f==1){
           if (num!=0){
               con.setMessage("添加失败!名称或编码重复！");
           }else {
               con.setMessage("添加失败！");
           }
       }
        return con;
    }

    @Override
    public Content updateCourse(Course course) {
        Content con =new Content();
        long num = courseMapper.checkCodeAndNum(course);
        int f=1;
        if (num==0){
            f = courseMapper.updateCourse(course) == 1 ? 0 : 1;
        }
        con.setFlag(f);
        if(f==0){
            con.setMessage("修改成功！");
            con.setContent(course);
        }else if (f==1){
            if (num != 0 ){
                con.setMessage("修改失败！名称或编码重复！");
            }else {
                con.setMessage("修改失败！");
            }
        }
        return con;
    }

    @Override
    public Content delCourse(String cids) {
        Content con =new Content();
        List<String> ids = Arrays.asList(cids.split(","));
        List<String> codes =  courseMapper.selectCodeById(ids);
        long a =courseMapper.checkSchedule(codes);
        int f=1;
        if (a==0){
             f = courseMapper.delCourse(ids) == 0 ? 0 : 1;
        }
        con.setFlag(f);
        if(f==0){
            con.setMessage("删除成功！");
        }else if (f==1){
            if (a!=0){
                con.setMessage("删除失败！当前课程已被课程表引用！");
            }else {
                con.setMessage("删除失败！");
            }
        }
        return con;
    }

    @Override
    public PageInfo<Course> listCouser(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return  new PageInfo<>(courseMapper.listCourse(params));
    }
}
