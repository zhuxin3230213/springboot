package com.gmunidata.schedule.mapper;

import com.gmunidata.schedule.model.CourseTime;
import com.gmunidata.schedule.model.Schedule;
import com.gmunidata.schedule.model.ScheduleNc;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ScheduleMapper {

    //添加课程
    int addSchedule(List<Schedule> list);

    //根据星期和目标节数查询课程信息
     List<String> selectIdBySundayAndNc(Map<String, Object> params);

    //删除课程
    int delSchedule(List<String> ids);

     //查询课程
     List<Schedule> listSchedule(Map<String, String> params);

    //根据开课时间表id 获取开课时间的开始日期和结束日期
     Map<String,Object> selectTimeById(String id);

    //增添课程的节数
      int addNc(List<ScheduleNc> scheduleNcs);

    //删除课程的节数
      int delNc(List<String> ids);

      //根据sch_id获取课程节数nc
    String selectNc(Map<String, String> params);

    //excel导出信息查询
    List<Map<String,Object>> excelOutSchedule(Map<String, String> params);

    //查询excel名称信息
    Map<String,String> getFileName(Map<String, String> params);


    List<Map<String,Object>> listWeekSchedule(Map<String, String> params);

    //获取班级编码和开课时间
    List<String> getClassCode(String className);

    List<String> getCourseTime(Map<String, Object> params);
    //根据课程名称获取课程编码
    String getCourseCode(String name);

    //获取要删除的课程表所有id
    List<String> selectIdByCourseTimeIdAndClassCode(Map<String, Object> params);

    List<Map<String, String>> getScheduleInfo(Map<String, String> params);
    List<Map<String, String>> getScheduleWeek(Map<String, String> params);

    //根据开课时间管理获取开课时间和结课时间
    CourseTime selectCourseTime(String id);

}
