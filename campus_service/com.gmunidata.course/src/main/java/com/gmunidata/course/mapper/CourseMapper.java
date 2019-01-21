package com.gmunidata.course.mapper;

import com.gmunidata.course.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CourseMapper {

    int addCourse(Course course);

    int updateCourse(Course course);

    int delCourse(List<String> ids);

    List<Course> listCourse(Map<String, String> params);

    long checkCodeAndNum(Course course);

    long checkSchedule(List<String> ids);

    List<String> selectCodeById (List<String> ids);
}
