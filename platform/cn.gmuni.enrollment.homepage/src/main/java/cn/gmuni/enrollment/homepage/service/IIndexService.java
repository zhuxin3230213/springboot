package cn.gmuni.enrollment.homepage.service;

import java.util.List;
import java.util.Map;

public interface IIndexService {
    List<Map<String,Object>> loadPvData(String format, String format1);

    List<Map<String,Object>> loadPvDataByMonth(String s, String s1);

    List<Map<String, Object>> loadModulePvData(String time);

    List<Map<String,Object>> loadPlanData(Map<String,String> params);

    List<Map<String,Object>> loadScoreData(Map<String,String> params);
}
