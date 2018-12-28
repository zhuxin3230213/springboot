package com.gmunidata.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public  class WeekUtil {

    //获取 某日是某年的第几周
    public static int getWeekByDay(String day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(format.parse(day));
        //设置从周一开始算新的一周
        calendar.setFirstDayOfWeek(2);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }


    //获取本年有多少周
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(c.getTime());
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static String[] getDates(String dateFrom, String dateEnd, String weekDays) {
        long time = 1l;
        long perDayMilSec = 24 * 60 * 60 * 1000;
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //需要查询的星期系数
        String strWeekNumber = weekForNum(weekDays);
        try {
            dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);
            while (true) {
                time = sdf.parse(dateFrom).getTime();
                time = time + perDayMilSec;
                Date date = new Date(time);
                dateFrom = sdf.format(date);
                if (dateFrom.compareTo(dateEnd) <= 0) {
                    //查询的某一时间的星期系数
                    Integer weekDay = dayForWeek(date);
                    //判断当期日期的星期系数是否是需要查询的
                    if (strWeekNumber.indexOf(weekDay.toString())!=-1) {
                        dateList.add(dateFrom);
                    }
                } else {
                    break;
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        String[] dateArray = new String[dateList.size()];
        dateList.toArray(dateArray);
        return dateArray;
    }
    //等到当期时间的周系数。星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
    public static Integer dayForWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到对应星期的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
     * @param weekDays 星期格式  星期一|星期二
     */
    public static String weekForNum(String weekDays){
        //返回结果为组合的星期系数
        String weekNumber = "";
        //解析传入的星期
        if(weekDays.indexOf("|")!=-1){//多个星期数
            String []strWeeks = weekDays.split("\\|");
            for(int i=0;i<strWeeks.length;i++){
                weekNumber = weekNumber + "" + getWeekNum(strWeeks[i]).toString();
            }
        }else{//一个星期数
            weekNumber = getWeekNum(weekDays).toString();
        }

        return weekNumber;

    }

    //将星期转换为对应的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
    public static Integer getWeekNum(String strWeek){
        Integer number = 1;//默认为星期日
        if("7".equals(strWeek)){
            number = 1;
        }else if("1".equals(strWeek)){
            number = 2;
        }else if("2".equals(strWeek)){
            number = 3;
        }else if("3".equals(strWeek)){
            number = 4;
        }else if("4".equals(strWeek)){
            number = 5;
        }else if("5".equals(strWeek)){
            number = 6;
        }else if("6".equals(strWeek)){
            number = 7;
        }
        return number;
    }

    //输入某一天获取是星期几
    public static int showWeekday(String day) throws Throwable {
        Calendar temp = Calendar.getInstance();// 需要初始化
        temp.set(Integer.parseInt(day.split("-")[0]),Integer.parseInt(day.split("-")[1])-1,Integer.parseInt(day.split("-")[2]));;// 月份从0开始
        int x = temp.get(Calendar.DAY_OF_WEEK);
        int sunday = 0 ;
        switch (x) {
            case Calendar.SUNDAY:
                sunday = 7;
                break;
            case Calendar.MONDAY:
                sunday = 1;
                break;
            case Calendar.TUESDAY:
                sunday = 2;
                break;
            case Calendar.WEDNESDAY:
                sunday = 3;
                break;
            case Calendar.THURSDAY:
                sunday = 4;
                break;
            case Calendar.FRIDAY:
                sunday = 5;
                break;
            case Calendar.SATURDAY:
                sunday = 6;
                break;
            default:
                break;
        }
        return sunday;
    }

}
