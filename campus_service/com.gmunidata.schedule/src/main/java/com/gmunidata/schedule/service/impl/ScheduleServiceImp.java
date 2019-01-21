package com.gmunidata.schedule.service.impl;

import cn.gmuni.utils.IdGenerator;
import com.gmunidata.base.response.Content;
import com.gmunidata.schedule.mapper.ScheduleMapper;
import com.gmunidata.schedule.model.CourseTime;
import com.gmunidata.schedule.model.Schedule;
import com.gmunidata.schedule.model.ScheduleNc;
import com.gmunidata.schedule.service.IScheduleService;
import com.gmunidata.schedule.utils.SheduleUtil;
import com.gmunidata.utils.DateUtils;
import com.gmunidata.utils.ExcelUtil;
import com.gmunidata.utils.MapToBeanUtil;
import com.gmunidata.utils.WeekUtil;
import io.swagger.models.auth.In;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScheduleServiceImp implements IScheduleService {

    @Autowired
    ScheduleMapper scheduleMapper;

    @Override
    public Content addSchedule(Map<String,Object> params) {
        Content con =new Content();
        Schedule schedule;
        List<String> ids = scheduleMapper.selectIdBySundayAndNc(params);

        //获取课程信息（这是一个list集合）
        List<Map<String,Object>> lisSch = (List<Map<String, Object>>) params.get("sch");
          if(lisSch.size()==0){
              //传入的数据为空，删除
              if (ids.size()!=0){
                  int f = scheduleMapper.delSchedule(ids) == ids.size() ? 0 : 1;
                  if(f==0){
                      scheduleMapper.delNc(ids);
                  }
              }
              con.setFlag(0);
              con.setMessage("删除成功！");
              return con;
          }
        List<Schedule> schedules =new ArrayList<>();
        //遍历课程信息的集合  将集合内部信息取出
        for (Map<String,Object> map:lisSch) {
            //放入原来的集合组成一个总的课程信息
            params.put("courseCode",map.get("courseCode")) ;//课程编码
            params.put("classroom",map.get("classroom"));//教室/
            params.put("teacher",map.get("teacher"));//老师
            params.put("week",map.get("week"));//上课的周数
            try {
                schedule =new Schedule();
                //将map内的数据映射成一个Schedule对象
                schedule = (Schedule) MapToBeanUtil.mapToObject(params,schedule.getClass());
                //将对象加入list集合
                schedules.add(schedule);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //校验周数是否超出范围
       if ((int)params.get("check") == 1){
            List<Map<String,Object>> listTemp = new ArrayList<>();
           for (Schedule schedule1:schedules) {
               Map<String,Object> mapTemp = new HashMap<>();
               mapTemp.put("week",schedule1.getWeek());
               mapTemp.put("sunday",schedule1.getSunday());
               listTemp.add(mapTemp);
           }
           String courseTimeId = (String)params.get("courseTimeId");
            con = checkSchedule(listTemp,courseTimeId);
            if (con.getFlag() == 1){
                return con;
            }else if (con.getFlag() == 0){
                //数据正确，删除原有数据
                if (ids.size()!=0){
                    int f = scheduleMapper.delSchedule(ids) == ids.size() ? 0 : 1;
                    if(f==0){
                        scheduleMapper.delNc(ids);
                    }
                }
            }

       }

      int f1 = add(schedules);
        con.setFlag(f1);
        if(f1 ==0){
            con.setMessage("操作成功！");
        }else{
            con.setMessage("操作失败！");
        }

        return con;
    }


    public int add(List<Schedule> schedules){
        List<Schedule> scheduless =new ArrayList<>();
        List<ScheduleNc> scheduleNcs =new ArrayList<>();
        int sum = 0;
        for (Schedule schedule:schedules) {
                //通过学年学期id查询学期开学日期和结束日期
       Map<String, Object> map = scheduleMapper.selectTimeById(schedule.getCourseTimeId());
       //开课日期和结课日期

        String sDate = DateUtils.date2String((Date) map.get("sDate"),DateUtils.LONG_DATE) ;
        String eDate =DateUtils.date2String((Date) map.get("eDate"),DateUtils.LONG_DATE) ;
        //获取课程所在的周几，在所有课程时间内的日期
        List<String> days = Arrays.asList(WeekUtil.getDates(sDate,eDate,schedule.getSunday()));
        //获取本次课程所需要安排的周
        List<String> weeks =Arrays.asList(schedule.getWeek().split(","));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //获取开学日期是第几周
           int fWeek = WeekUtil.getWeekByDay(sDate);
            //循环遍历，看获取的日期分别是第几周
            for (String str:days) {
                //获取日期对应的事第几周（转换为String类型）
                String num ="";
                if (WeekUtil.getWeekByDay(str) >=  fWeek){
                  num = Integer.toString (WeekUtil.getWeekByDay(str)-fWeek+1);
                }else{
                 num = Integer.toString (WeekUtil.getWeekByDay(str)+WeekUtil.getMaxWeekNumOfYear(Integer.parseInt(sDate.substring(0,4)))-fWeek+1);
                }
                //循环遍历课表有安排课程的周
                for (String week : weeks) {
                    //如果日期对应的周数与安排的课程的周数相同则代表这一天有课
                    if (num.equals(week)){
                        Schedule schedule1 =new Schedule();
                        BeanUtils.copyProperties(schedule,schedule1);
                        //设置具体日期，和所属周数（第几周），以及id
                        schedule1.setDay(sdf.parse(str));
                        schedule1.setWeek(week);
                        schedule1.setId(IdGenerator.getId());
                        //将课程信息添加到集合中
                        scheduless.add(schedule1);
                       List<String> ncs =Arrays.asList(schedule1.getNc().split(","));
                        ScheduleNc scheduleNc;
                        for (String nc:ncs) {
                            scheduleNc =new ScheduleNc();
                            scheduleNc.setNc(nc);
                            scheduleNc.setSchId(schedule1.getId());
                            scheduleNc.setId(IdGenerator.getId());
                            scheduleNcs.add(scheduleNc);
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
        if (scheduless.size() != 0 && scheduleNcs.size() != 0) {
            int num = scheduleMapper.addSchedule(scheduless) == scheduless.size() ? 0 : 1;
            int num1 = scheduleMapper.addNc(scheduleNcs) == scheduleNcs.size() ? 0 : 1;
            if (num == 0 || num1 == 0) {
                return 0;
            }
        }
        return 1;
    }



    @Override
    public Content delSchedule(String ids) {
        Content con =new Content();
       List<String> list =Arrays.asList(ids.split(","));
        int f = scheduleMapper.delSchedule(list) == 0 ? 0 : 1;
        con.setFlag(f);
        if(f==0){
            con.setMessage("删除成功！");
        }else {
            con.setMessage("删除失败！");
        }
        return con;
    }

    @Override
    public List<Schedule> listSchedule(Map<String,String> params) {
        List<Schedule> schedules =scheduleMapper.listSchedule(params);
        Map<String,String> ncMap=new HashMap<>();
        for (Schedule schedule:schedules) {
            ncMap.put("schId",schedule.getId());
            String nc = scheduleMapper.selectNc(ncMap);
            schedule.setNc(nc);
        }
        return schedules;
    }

    /**
     * 导出课程表
     * @param res
     */
    @Override
    public void outSchedule(Map<String,String> params,HttpServletResponse res) {
        try {
            InputStream is = null;
            XSSFWorkbook workbook = null;
            XSSFSheet sheet = null;
            Row row = null;
            Cell cell =null;
            Resource resource = new ClassPathResource("schedule.xlsx");
            is =resource.getInputStream();;// 将excel文件转为输入流
            workbook = new XSSFWorkbook(is);// 创建个workbook，
            // 获取第一个sheet
            sheet = workbook.getSheetAt(0);
            List<Map<String,Object>> excelListMap  = scheduleMapper.excelOutSchedule(params);
            String fileName = "课程表模板.xlsx";
            if (excelListMap.size()!=0){
               Map<String,String> name = scheduleMapper.getFileName(params);
               if (name.size()!=0) {
                   List<String>  str = Arrays.asList(sheet.getRow(0).getCell(0).getStringCellValue().split(":"));
                   fileName = name.get("academicYear")+"学年第"+ name.get("semester") + "学期" + name.get("className") + "班课程表";
               }
                List< Map<String,Object>> tempMap =new ArrayList<>();
                for (int i = 0; i <excelListMap.size() ; i++) {
                    Map<String,Object> temp=null;
                    if (tempMap.size()==0){
                        temp =new HashMap<>();
                        temp.put("num",((String)excelListMap.get(i).get("schedule")).split("`")[0]);
                        temp.put("schedule",((String)excelListMap.get(i).get("schedule")).split("`")[1]);
                        temp.put("nc",excelListMap.get(i).get("nc"));
                        temp.put("startNc",excelListMap.get(i).get("startNc"));
                        tempMap.add(temp);
                    }else{
                        boolean f =true;
                        for (int j = 0; j <tempMap.size() ; j++) {
                            if (((String)excelListMap.get(i).get("schedule")).split("`")[1].equals(tempMap.get(j).get("schedule"))){
                                tempMap.get(j).put("num",tempMap.get(j).get("num")+","+((String)excelListMap.get(i).get("schedule")).split("`")[0]);
                                f=false;
                            }
                        }
                        if (f){
                            temp =new HashMap<>();
                            temp.put("num",((String)excelListMap.get(i).get("schedule")).split("`")[0]);
                            temp.put("schedule",((String)excelListMap.get(i).get("schedule")).split("`")[1]);
                            temp.put("nc",excelListMap.get(i).get("nc"));
                            temp.put("startNc",excelListMap.get(i).get("startNc"));
                            tempMap.add(temp);
                        }
                    }
                }
                SheduleUtil.writeExcel(workbook,sheet,tempMap);
            }
        row = sheet.getRow(0);
        cell =row.getCell(0);
        cell.setCellValue(fileName);
        res.setHeader("content-type", "pplication/vnd.ms-excel");
        res.setContentType("UTF-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        ExcelUtil.downExcel(res,workbook);
    } catch (Exception e) {
        e.printStackTrace();
          }

    }

    /**
     * 导出课程表模板
     * @param res
     */
    @Override
    public  void modelSchedule(HttpServletResponse res) {
            String fileName = "schedule.xlsx";
            XSSFWorkbook workbook = ExcelUtil.modelOut(res,fileName);
            ExcelUtil.downExcel(res,workbook);
    }

    @Override
    public Map<String, Object> getAppSchedule(Map<String,String> params){
        Map<String, Object> data = new HashMap<>();
        Date currentTime = new Date();
        //获取到一周的所有课程信息
        List<Map<String, Object>> weekSchedules = scheduleMapper.listWeekSchedule(params);
        //处理日期类型转为字符串
        for (int i = 0; i < weekSchedules.size(); i++) {
            Map<String, Object> item = weekSchedules.get(i);
            item.put("day", DateUtils.date2String((Date)item.get("day"),"yyyy-MM-dd"));
        }
        data.put("weekSchedules", weekSchedules);
        return data;
    }
    @Override
    public  List<Map<String, String>> getScheduleInfo(Map<String,String> params){
        return scheduleMapper.getScheduleInfo(params);
    }
    @Override
    public Map<String, String> getScheduleWeek(Map<String,String> params){
        Map<String, String> res = new HashMap<>();
        res.put("week", "");
        List<Map<String, String>> scheduleWeek = scheduleMapper.getScheduleWeek(params);
        if(scheduleWeek != null && scheduleWeek.size() > 0){
            String weekStr = "";
            int preWeek = 0;
            for (int i = 0; i < scheduleWeek.size(); i++) {
                String week = scheduleWeek.get(i).get("week");
                if(i == 0){
                    weekStr += week + "-";
                }
                if(preWeek + 1 == Integer.parseInt(week)){
                    preWeek = Integer.parseInt(week);
                    continue;
                }
                if (preWeek + 1 < Integer.parseInt(week) && preWeek > 0){
                    weekStr += preWeek + "周 " + week + "-";
                }
                preWeek = Integer.parseInt(week);
            }
            weekStr += preWeek + "周";
            res.put("week", weekStr);
        }
        return res;
    }

    /**
     * 导入课程表
     * @param params
     * @return
     */
    @Override
    public Content inSchedule(Map<String, Object> params) {
        Content con =new Content();
        String  uuid  = (String) params.get("uuid");
        File file = new File(ExcelUtil.basePath +File.separator+uuid);
        Workbook workbook = null;
        try {
            if (file.getName().substring(uuid.indexOf(".")).equals(".xls")) {
                workbook = new HSSFWorkbook(new FileInputStream(file));
            } else if (file.getName().substring(uuid.indexOf(".")).equals(".xlsx")) {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            } else {
                con.setFlag(0);
                con.setMessage("此文件不是excel文件");
                return con;
            }
        Sheet sheet = workbook.getSheet((String) params.get("name"));

          //head 为 "xxxx-xxxx年第x学期xx班级程表
            List<String> classCodeList = scheduleMapper.getClassCode((String) params.get("className"));
            String  classCode = "";
            if (classCodeList.size() == 1){
                classCode =  classCodeList.get(0);
            }else {
                con.setFlag(1);
                con.setMessage("班级不存在，无法导入！");
                return con;
            }
            String   courseTimeId =  (String) params.get("courseTimeId");
            List<Map<String, Object>> listMaps = new ArrayList<>();

            //获取所有的合并单元格
            for (int i = 2; i < 12; i++) {
                for (int j = 1; j < 8; j++) {
                    String str = ExcelUtil.resCell(sheet.getRow(i).getCell(j));
                    if (str != null && !"".equals(str)) {
                        String[] s = str.split("\\n");
                        if (s.length % 4 == 0) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("classCode", classCode);
                            map.put("courseTimeId", courseTimeId);
                            map.put("nc", SheduleUtil.getAllCell(sheet, i, j));
                            map.put("targetNc", i - 1);
                            map.put("sunday", j);
                            List<Map<String, Object>> lisSch = new ArrayList<>();
                            for (int k = 0; k < s.length; k = k + 4) {
                                Map<String, Object> sch = new HashMap<>();
                                sch.put("courseCode", scheduleMapper.getCourseCode(s[k].trim()));//课程编码
                                sch.put("teacher", s[k + 2].trim());//老师
                                sch.put("classroom", s[k + 3].trim());//教室
                                 String week = SheduleUtil.getWeek(s[k + 1].trim());
                                 if (!"".equals(week)){
                                sch.put("week", SheduleUtil.getWeek(s[k + 1].trim()));//上课的周数
                                 }else {
                                     con.setFlag(1);
                                     con.setMessage("周数请按照{第x,x-x周}格式填写，并不能为空！");
                                     return con;
                                 }
                                lisSch.add(sch);
                            }
                            map.put("sch", lisSch);
                            listMaps.add(map);
                        } else {
                            con.setFlag(1);
                            String s1 = sundayByInt(j);

                            con.setMessage("第" + (i + 1) + "行，" + s1 + "，数据格式异常，无法导入！");
                            return con;
                        }

                    }

                }
            }

            if (listMaps.size() != 0) {
                List<Map<String,Object>> listTemp = new ArrayList<>();
                for (Map<String,Object> map:listMaps) {
                    int sunday = (int)map.get("sunday");
                    int targetNc = (int)map.get("targetNc");
                    List<Map<String,Object>> lisSch = (List<Map<String, Object>>) map.get("sch");
                    for (Map<String,Object> map1 :lisSch) {
                        Map<String,Object> mapTemp = new HashMap<>();
                        mapTemp.put("targetNc",targetNc);
                        mapTemp.put("sunday",sunday);
                        mapTemp.put("week",map1.get("week"));
                        listTemp.add(mapTemp);
                    }
                }
               con = checkSchedule(listTemp,courseTimeId);
                if (con.getFlag() == 1 ){
                    return con;
                }
                //校验周数是否冲突
                con = checkWeek(listTemp);
                if (con.getFlag() == 1){
                    return  con;
                }

                Map<String, Object> getIdMap = new HashMap<>();
                getIdMap.put("classCode", classCode);
                getIdMap.put("courseTimeId", courseTimeId);
                List<String> list = scheduleMapper.selectIdByCourseTimeIdAndClassCode(getIdMap);
                if (list.size() != 0) {
                    int f = scheduleMapper.delSchedule(list) == list.size() ? 0 : 1;
                    if (f == 0) {
                        scheduleMapper.delNc(list);
                    }
                }
                int i = 0;
                for (Map<String, Object> map : listMaps) {
                     map.put("check",0);
                    Content con1 = addSchedule(map);
                    if (con1.getFlag() == 0) {
                        i++;
                    }
                }
                if (listMaps.size() == i) {
                    con.setFlag(0);
                    con.setMessage("导入成功！");
                } else if (listMaps.size() < i) {
                    con.setFlag(0);
                    con.setMessage("可能因为课程时间安排，导致一部分数据无法导入！");
                }

            } else {
                con.setFlag(1);
                con.setMessage("excel中没有可以导入的数据！");
            }

        }catch (Exception e){
            e.printStackTrace();
            con.setFlag(1);
            con.setMessage("导入失败!");
            return con;
        }
        return con;
    }


    //校验周数是否冲突
    private Content checkWeek(List<Map<String,Object>> listMaps) {
        Content con = new Content();
        for (int i = 0; i < listMaps.size()-1 ; i++) {
             int sunday =  (int)listMaps.get(i).get("sunday");
             int targetNc =  (int)listMaps.get(i).get("targetNc");
            for (int j = i+1; j < listMaps.size() ; j++) {
                int sundayTemp =  (int)listMaps.get(j).get("sunday");
                int targetNcTemp =  (int)listMaps.get(j).get("targetNc");
                //如果两组数据的星期和目标节数一致，则比较周数是否已相同的
                if (sunday == sundayTemp && targetNc == targetNcTemp){
                    String week = (String) listMaps.get(i).get("week");
                    String[] weeks = week.split(",");
                    String weekTemp = (String) listMaps.get(j).get("week");
                    String[] weeksTemp = weekTemp.split(",");
                    Map<String,Integer> map = new HashMap<>();
                    for (int k = 0; k < weeks.length; k++) {
                        map.put(weeks[k],k);
                    }
                    for (int k = 0; k < weeksTemp.length ; k++) {
                        map.put(weeksTemp[k],k);
                    }
                    if (map.size() != (weeks.length + weeksTemp.length)){
                        con.setFlag(1);
                        con.setMessage(sundayByInt(sunday)+"课程安排存在周数冲突！");
                        return con;
                    }

                }

            }
        }
        con.setFlag(0);
        return con;
    }
    //根据传入的数据获取星期几
    private String sundayByInt(int j){
        String s = null;
        if (j==1){ s = "星期一";}
        if (j==2){ s = "星期二";}
        if (j==3){ s = "星期三";}
        if (j==4){ s = "星期四";}
        if (j==5){ s = "星期五";}
        if (j==6){ s = "星期六";}
        if (j==7){ s = "星期日";}
        return s;
    }

    //获取开课和结课的周数和星期
    private Map<String,Object> getScheduleSAndE(String courseTimeId){
        Map<String,Object> map =new HashMap<>();
        CourseTime courseTime = scheduleMapper.selectCourseTime(courseTimeId);
        String sDate = DateUtils.date2String(courseTime.getsDate(),DateUtils.LONG_DATE) ;
        String eDate =DateUtils.date2String(courseTime.geteDate(),DateUtils.LONG_DATE) ;
        try {
            //开始周数
            int fWeek = WeekUtil.getWeekByDay(sDate);
            //结束周数
            int lWeek = WeekUtil.getWeekByDay(eDate);

            if (lWeek >=  fWeek){
                lWeek = (lWeek-fWeek+1);
            }else{
                lWeek = (lWeek+WeekUtil.getMaxWeekNumOfYear(Integer.parseInt(sDate.substring(0,4)))-fWeek+1);
            }
            int fday = WeekUtil.showWeekday(sDate);
            int lday = WeekUtil.showWeekday(eDate);
            map.put("minWeek",1);
            map.put("minSunday",fday);
            map.put("maxWeek",lWeek);
            map.put("maxSunday",lday);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
    }
    return map;
   }

   //校验所添加的课程是否超出学期范围
    private  Content checkSchedule ( List<Map<String,Object>> listTemp,String courseTimeId){
        Content con =new Content();
        Map<String,Object>  map = getScheduleSAndE(courseTimeId);
        int minWeek = (int)map.get("minWeek");
        int minSunday = (int)map.get("minSunday");
        int maxWeek = (int)map.get("maxWeek");
        int maxSunday = (int)map.get("maxSunday");
        for (Map<String,Object> mapTemp:listTemp) {
            int sunday = 0;
            if (mapTemp.get("sunday") instanceof  String ){
               sunday = Integer.parseInt((String) mapTemp.get("sunday") );
            }else if (mapTemp.get("sunday") instanceof Integer){
                sunday = (int) mapTemp.get("sunday");
            }
            if (sunday != minSunday){
                //将周数转化为int数组排序
                String[] str = String.valueOf(mapTemp.get("week")).split(",");
                int[] ints = new  int[str.length];
                for (int i = 0; i <str.length ; i++) {
                    ints[i] = Integer.parseInt(str[i]);
                }
                Arrays.sort(ints);
                if (sunday < minSunday){
                    if ( ints[0] <= minWeek){
                        con.setFlag(1);
                        con.setMessage("所添加的课程超出学期限制!此学期从第"+minWeek+"周"+sundayByInt(minSunday)+"—第"+maxWeek+"周"+sundayByInt(maxSunday));
                        return con;
                    }
                }
                if (sunday > maxSunday) {
                        //最大周数大于学期最大周数，或者等于最大周且星期数超出最后一周的天数
                        if (ints[ints.length - 1] >= maxWeek ) {
                            con.setFlag(1);
                            con.setMessage("所添加的课程超出学期限制!此学期从第" + minWeek + "周" + sundayByInt(minSunday) + "—第" + maxWeek + "周" + sundayByInt(maxSunday));
                            return con;
                        }
                    }
                if (sunday < maxSunday && sunday > minSunday){
                    if (ints[ints.length - 1] > maxWeek ) {
                        con.setFlag(1);
                        con.setMessage("所添加的课程超出学期限制!此学期从第" + minWeek + "周" + sundayByInt(minSunday) + "—第" + maxWeek + "周" + sundayByInt(maxSunday));
                        return con;
                    }
                 }
            }
        }
        con.setFlag(0);
        return con;

    }
}
