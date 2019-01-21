package com.gmunidata.schedule.service;

import com.gmunidata.base.response.BaseResponse;
import com.gmunidata.base.response.Content;
import com.gmunidata.schedule.model.Schedule;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IScheduleService {

     Content addSchedule(Map<String, Object> params);

     Content delSchedule(String ids);

     List<Schedule> listSchedule(Map<String, String> params);

     /**
      * 导出课程表
      * @return
      */
     void  outSchedule(Map<String, String> params, HttpServletResponse response);
     /**
      * 导出课程表模板
      * @return
      */
     void  modelSchedule(HttpServletResponse response);

     /**
      * 导入课程表
      * @return
      */
     Content  inSchedule(Map<String, Object> params);

     Map<String, Object> getAppSchedule(Map<String, String> params);

     List<Map<String, String>> getScheduleInfo(Map<String, String> params);
     Map<String, String> getScheduleWeek(Map<String, String> params);
}
