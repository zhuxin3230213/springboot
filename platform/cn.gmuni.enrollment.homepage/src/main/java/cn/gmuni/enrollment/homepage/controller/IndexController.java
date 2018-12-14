package cn.gmuni.enrollment.homepage.controller;

import cn.gmuni.enrollment.homepage.service.IIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "enrollment/index")
public class IndexController {

    @Autowired
    @Qualifier(value = "indexServiceImpl")
    private IIndexService indexService;

    /**
     * 加载访问量趋势
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping("/loadPvData")
    public List<Map<String, Object>> loadPvData(@RequestBody Map<String, String> params) {
        String type = params.get("type");
        Date start = null;
        Date end = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String fmt = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        switch (type) {
            //获取本周第一天与最后一天
            case "0": {
                int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
                if (dayofweek == 1) {
                    dayofweek += 7;
                }
                cal.add(Calendar.DATE, 2 - dayofweek);
                start = cal.getTime();
                cal.add(Calendar.DAY_OF_WEEK, 6);
                end = cal.getTime();
                break;
            }
            //获取本月第一天与最后一天
            case "1": {
                cal.set(Calendar.DAY_OF_MONTH, 1);
                start = cal.getTime();
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
                end = cal.getTime();
                break;
            }
            case "2": {
                int year = cal.get(Calendar.YEAR);
                try {
                    start = sdf.parse(year + "-01-01");
                    end = sdf.parse(year + "-12-31");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //对于本年度的需要按月查询
        if ("2".equals(type)) {
            return indexService.loadPvDataByMonth(sdf.format(start) + " 00:00:00", sdf.format(end) + " 23:59:59");
        } else {
            return indexService.loadPvData(sdf.format(start) + " 00:00:00", sdf.format(end) + " 23:59:59");
        }
    }



    @ResponseBody
    @PostMapping("/loadModulePvData")
    public List<Map<String,Object>> loadModulePvData(@RequestBody Map<String,String> params){
        return indexService.loadModulePvData(params.get("date"));
    }

    @ResponseBody
    @PostMapping("/loadPlanData")
    public List<Map<String,Object>> loadPlanData(@RequestBody Map<String,String> params) {
        return indexService.loadPlanData(params);
    }

    @ResponseBody
    @PostMapping("/loadScoreData")
    public List<Map<String,Object>> loadScoreData(@RequestBody Map<String,String> params){
        return indexService.loadScoreData(params);
    }
}
