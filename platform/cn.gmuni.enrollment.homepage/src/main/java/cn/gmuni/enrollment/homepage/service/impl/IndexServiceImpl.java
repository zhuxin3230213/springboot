package cn.gmuni.enrollment.homepage.service.impl;

import cn.gmuni.enrollment.homepage.mapper.IndexMapper;
import cn.gmuni.enrollment.homepage.service.IIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IndexServiceImpl implements IIndexService {

    @Autowired
    private IndexMapper mapper;

    @Override
    public List<Map<String, Object>> loadPvData(String start, String end) {
        List<Map<String, Object>> result = mapper.loadPvByDateRange(start,end);
        return fillDate(start, end, result, "yyyy-MM-dd",Calendar.DATE);
    }

    @Override
    public List<Map<String, Object>> loadPvDataByMonth(String start, String end) {
        List<Map<String, Object>> result =  mapper.loadPvDataByMonth(start,end);
        return fillDate(start, end, result, "yyyy-MM", Calendar.MONTH);
    }

    @Override
    public List<Map<String, Object>> loadModulePvData(String time) {
        return mapper.loadModulePvByDate(time);
    }

    @Override
    public List<Map<String, Object>> loadPlanData(Map<String, String> params) {
        return mapper.loadPlanData(params);
    }

    @Override
    public List<Map<String, Object>> loadScoreData(Map<String, String> params) {
        return mapper.loadScoreData(params);
    }

    /**
     * 补全日期
     * @param start
     * @param end
     * @param result
     * @param s
     * @param month
     * @return
     */
    private List<Map<String, Object>> fillDate(String start, String end, List<Map<String, Object>> result, String s, int month) {
        List<Map<String, Object>> data = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat(s);
        try {
            Date dt = fmt.parse(start);
            Date et = fmt.parse(end);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            for (Map<String, Object> r : result) {
                Date t = cal.getTime();
                while (!fmt.format(t).equals(r.get("time")) && !t.after(et)) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("time", fmt.format(t));
                    m.put("num", 0);
                    data.add(m);
                    cal.add(month, 1);
                    t = cal.getTime();
                }
                if (fmt.format(t).equals(r.get("time"))) {
                    data.add(r);
                    cal.add(month, 1);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }
}
