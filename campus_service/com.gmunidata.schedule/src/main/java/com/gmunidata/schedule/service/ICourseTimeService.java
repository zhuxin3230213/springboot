package com.gmunidata.schedule.service;

import com.gmunidata.base.response.Content;
import com.gmunidata.schedule.model.CourseTime;

import java.util.List;
import java.util.Map;

public interface ICourseTimeService {

     Content addCourseTime(CourseTime courseTime);

    Content updateCourseTime(CourseTime courseTime);

    Content delCourseTime(String ids);

    List<CourseTime> listCourseTime(Map<String, String> params);

    List<CourseTime> getCourseTime(Map<String, String> params);
}
