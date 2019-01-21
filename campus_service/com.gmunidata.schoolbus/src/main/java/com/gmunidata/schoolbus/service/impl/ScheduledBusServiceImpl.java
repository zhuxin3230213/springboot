package com.gmunidata.schoolbus.service.impl;

import cn.gmuni.lookup.container.LookupCache;
import cn.gmuni.lookup.model.LookupTree;
import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.gmunidata.base.response.Content;
import com.gmunidata.schoolbus.mapper.ScheduledBusMapper;
import com.gmunidata.schoolbus.model.ScheduledBus;
import com.gmunidata.schoolbus.service.ScheduledBusService;
import com.gmunidata.utils.DateUtils;
import com.gmunidata.utils.ExcelUtil;
import com.gmunidata.utils.LookUpUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sonatype.aether.impl.internal.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class ScheduledBusServiceImpl implements ScheduledBusService {

    @Autowired
    ScheduledBusMapper scheduledBusMapper;

    private static final  String[] SCHEDULED_BUS = {"时段","起止点","乘车点","时间","载客量(人)"};

    @Override
    public Content addScheduledBus(ScheduledBus scheduledBus) {
        Content con = new Content();
        List<String> seasons = scheduledBusMapper.checkSeason(scheduledBus);
        if (seasons.size()!= 0 && scheduledBus.getSeason().equals("0")){
            con.setFlag(1);
            con.setMessage("此时间段安排的校车或司机存在冲突");
            return con;
        }
        boolean flag =true;
        for (int i = 0; i <seasons.size() ; i++) {
            if (seasons.get(i).equals(scheduledBus.getSeason()) || seasons.get(i).equals("0")){
                flag=false;
                break;
            }
        }
        if (flag == false) {
            con.setFlag(1);
            con.setMessage("此时间段安排的校车或司机存在冲突");
            return con;
        }
        scheduledBus.setId(IdGenerator.getId());
        int f = scheduledBusMapper.addScheduledBus(scheduledBus) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("添加成功");
        }else {
            con.setMessage("添加失败");
        }
        return con;
    }

    @Override
    public Content updateScheduledBus(ScheduledBus scheduledBus) {
        Content con = new Content();
        List<String> seasons = scheduledBusMapper.checkSeason(scheduledBus);
        if (seasons.size()!= 0 && scheduledBus.getSeason().equals("0")){
            con.setFlag(1);
            con.setMessage("此时间段安排的校车或司机存在冲突");
            return con;
        }
        boolean flag =true;
        for (int i = 0; i <seasons.size() ; i++) {
            if (seasons.get(i).equals(scheduledBus.getSeason()) || seasons.get(i).equals("0")){
                flag=false;
                break;
            }
        }
        if (flag == false) {
            con.setFlag(1);
            con.setMessage("此时间段安排的校车或司机存在冲突");
            return con;
        }
        int f = scheduledBusMapper.updateScheduledBus(scheduledBus) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("修改成功");
        }else {
            con.setMessage("修改失败");
        }
        return con;
    }

    @Override
    public Content delScheduledBus(List<String> ids) {
        Content con = new Content();
        int f = scheduledBusMapper.delScheduledBus(ids) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("删除成功");
        }else {
            con.setMessage("删除失败");
        }
        return con;
    }

    @Override
    public List<Map<String, Object>> listScheduledBus(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return scheduledBusMapper.listScheduledBus(params);
    }

    @Override
    public List<String> getSchoolBusCode() {
        return scheduledBusMapper.getSchoolBusCode();
    }

    @Override
    public List<Map<String,Object>> getSchoolBusDrivceCode() {
        return scheduledBusMapper.getSchoolBusDrivceCode();
    }

    @Override
    public void outScheduledBus(HttpServletResponse res) {
        List<Map<String,Object>> list = scheduledBusMapper.outScheduledBus();
        if (list.size() == 0){return;}
        List<Map<String,Object>> summer = new ArrayList<>();
        List<Map<String,Object>> winter = new ArrayList<>();
        //定义一个变量统计季节是通用的个数
        int m = 0;
        for (int i = 0; i < list.size() ; i++) {
            if (list.get(i).get("season").equals("0")){
                summer.add(list.get(i));
                winter.add(list.get(i));
                m++;
            }else if (list.get(i).get("season").equals("1")){
                summer.add(list.get(i));
            }else {
                winter.add(list.get(i));
            }
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        //全部是通用情况
        if (m == list.size()){
            XSSFSheet sheet = workbook.createSheet("通用");
            writeSchdeuled(sheet,list,"通勤班车时刻表");
        }else {
            //夏季部分
            if (summer.size()!=0){
                XSSFSheet sheet = workbook.createSheet("夏季");
                writeSchdeuled(sheet,summer,"夏季通勤班车时刻表");
            }
            //冬季部分
            if(winter.size()!= 0){
                XSSFSheet sheet = workbook.createSheet("冬季");
                writeSchdeuled(sheet,winter,"冬季通勤班车时刻表");
            }
        }
        ExcelUtil.downExcel(res,workbook);
    }

    //写班车通勤表的excel
    private static void writeSchdeuled(XSSFSheet sheet,List<Map<String,Object>> list,String name){

        List<String> title = Arrays.asList(SCHEDULED_BUS);
        //创建单元格
        for (int i = 0; i < list.size()+2 ; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < title.size() ; j++) {
             row.createCell(j);
            }
        }
        //he并第一行单元格并输入
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        sheet.getRow(0).getCell(0).setCellValue(name);
        for (int i = 0; i < title.size() ; i++) {
            sheet.getRow(1).getCell(i).setCellValue(title.get(i));
        }
        //设置校车内容
        Map<String,Object> temp;
        //获取缓存的内容
        List<LookupTree> listTemp = LookupCache.list();
        Map<String,LookupTree> lookups = new HashMap<>();
        for (LookupTree ca : listTemp) {
            lookups.put(ca.getCode(), ca);
        }
        for (int i = 0; i < list.size() ; i++) {
            Row row  = sheet.createRow(i+2);
            for (int j = 0; j < title.size() ; j++) {
                temp = list.get(i);
                row.createCell(0).setCellValue(LookUpUtil.codeByName("fcsd",String.valueOf(temp.get("runningTimetype")),lookups));
                String origin = LookUpUtil.codeByName("xq",String.valueOf(temp.get("origin")),lookups);
                String destination = LookUpUtil.codeByName("xq",String.valueOf(temp.get("destination")),lookups);
                row.createCell(1).setCellValue(origin+"—"+destination);
                row.createCell(2).setCellValue(String.valueOf(temp.get("ridingPoint")));
                row.createCell(3).setCellValue(String.valueOf(temp.get("time")));
                row.createCell(4).setCellValue(String.valueOf(temp.get("volume")));
            }
        }
    }


    @Override
    public List<String> getSeasonList() {
        return scheduledBusMapper.getSchoolSeasonList();
    }

    @Override
    public Map<String, String> schoolBusTimeList(Map<String, String> map) {
        Map<String, String> schoolBus = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("season", map.get("season"));
        queryParams.put("week", map.get("week"));
        queryParams.put("startCampus", map.get("startCampus"));
        queryParams.put("endCampus", map.get("endCampus"));
        List<Map<String, String>> schoolBusTimeList = scheduledBusMapper.getSchoolBusTimeList(queryParams);
        if (schoolBusTimeList.size() != 0){
            String address = schoolBusTimeList.get(0).get("address");
            //将时间进行排序
            List<String> list = ascTime(schoolBusTimeList);
            //将时间拼接成字符串
            String timeList = String.join("   ", list);
            schoolBus.put("address", address);
            schoolBus.put("timeList",timeList);
        }else {
            schoolBus.put("address","");
            schoolBus.put("timeList","暂无安排");
        }
        return schoolBus;
    }

    @Override
    public List<Map<String, String>> getSchoolBusCampusList() {
        return  scheduledBusMapper.getSchoolCampusList();
    }

    /**
     * 将一组 HH:mm 字符串数据进行排序
     * @param schoolBusTimeList
     * @return
     */
    private List<String> ascTime(List<Map<String, String>> schoolBusTimeList) {
        List<Long> timeList = new ArrayList<>();
        for (Map<String, String> temp : schoolBusTimeList) {
            Date sTime = DateUtils.string2Date(temp.get("sTime"), DateUtils.HOUR_MIN);
            long time = sTime.getTime();
            timeList.add(time);
        }
        Collections.sort(timeList); //升序排列
        List<String> ascTime = new ArrayList<>();
        for (Long temp : timeList) {
            Date date = DateUtils.long2Date(temp);
            String s = DateUtils.date2String(date, DateUtils.HOUR_MIN);
            ascTime.add(s);
        }
        return ascTime;
    }
}
