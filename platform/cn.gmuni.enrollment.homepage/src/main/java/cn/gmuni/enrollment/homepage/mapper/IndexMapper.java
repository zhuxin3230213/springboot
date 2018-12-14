package cn.gmuni.enrollment.homepage.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IndexMapper {
    List<Map<String,Object>> loadPvByDateRange(@Param("start") String start,@Param("end") String end);

    List<Map<String,Object>> loadPvDataByMonth(@Param("start") String start,@Param("end") String end);

    List<Map<String,Object>> loadModulePvByDate(@Param("date") String time);

    List<Map<String,Object>> loadPlanData(Map<String,String> params);

    List<Map<String,Object>> loadScoreData(Map<String,String> params);
}
