package com.gmunidata.schedule.mapper;

import com.gmunidata.schedule.model.CourseTime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CourseTimeMapper {

    int addCourseTime(CourseTime courseTime);

    int updateCourseTime(CourseTime courseTime);

    int delCourseTime(List<String> ids);

    List<CourseTime> listCourseTime(Map<String, String> params);

    List<CourseTime> getCourseTime(Map<String, String> params);

    long  checkCourseTime(CourseTime courseTime);

    long  selectScheduleNum(List<String> lists);


}
