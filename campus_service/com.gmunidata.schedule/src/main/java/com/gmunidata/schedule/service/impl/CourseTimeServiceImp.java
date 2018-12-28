package com.gmunidata.schedule.service.impl;

import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.gmunidata.base.response.Content;
import com.gmunidata.schedule.mapper.CourseTimeMapper;
import com.gmunidata.schedule.model.CourseTime;
import com.gmunidata.schedule.service.ICourseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseTimeServiceImp implements ICourseTimeService {

    @Autowired
    CourseTimeMapper courseTimeMapper;


    @Override
    public Content addCourseTime(CourseTime courseTime) {
        Content con = new Content();
        long num  = courseTimeMapper.checkCourseTime(courseTime);
       int  f = 2;
        if (num == 0) {
            courseTime.setId(IdGenerator.getId());
            courseTime.setCreateTime(new Date());
            f = courseTimeMapper.addCourseTime(courseTime) == 1 ? 0 : 1;
        }
       con.setFlag(f);
       if(f == 0){
           con.setMessage("添加成功！");
           con.setContent(courseTime);
       } else if (f == 1) {
           con.setMessage("添加失败！");
       } else {
           con.setMessage("重复添加！");
       }
        return con;
    }

    @Override
    public Content updateCourseTime(CourseTime courseTime) {
        Content con = new Content();
        List<String> couIds = new ArrayList<>();
        couIds.add(courseTime.getId());
        long num1  = courseTimeMapper.selectScheduleNum(couIds);
        if (num1 != 0){
            con.setFlag(1);
            con.setMessage("该开课时间下存在课程，不能被修改和删除！");
            return con;
        }
        long num  = courseTimeMapper.checkCourseTime(courseTime);
        int  f = 1;
        if(num == 0){
            f = courseTimeMapper.updateCourseTime(courseTime) == 1 ? 0 : 1;
        }
        con.setFlag(f);
        if(f == 0){
            con.setMessage("修改成功！");
            con.setContent(courseTime);
        }else {
            if(num != 0){
                con.setMessage("修改的信息与已有的信息重复！");
            }else{
                con.setMessage("修改失败！");
            }
        }
        return con;
    }

    @Override
    public Content delCourseTime(String ids) {
        Content con =new Content();
        List<String> couIds = Arrays.asList(ids.split(","));
        long num1  = courseTimeMapper.selectScheduleNum(couIds);
        if (num1 != 0){
            con.setFlag(1);
            con.setMessage("所删除的开课时间下存在课程，不能被修改和删除！");
            return con;
        }
        int f = courseTimeMapper.delCourseTime(couIds) > 0 ? 0 : 1;
        con.setFlag(f);
        if(f==0){
            con.setMessage("删除成功！");
        }else{
            con.setMessage("删除失败！");
        }
        return con;
    }

    @Override
    public List<CourseTime> listCourseTime(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return courseTimeMapper.listCourseTime(params);
    }

    @Override
    public List<CourseTime> getCourseTime(Map<String,String> params) {
        return courseTimeMapper.getCourseTime(params);
    }
}
