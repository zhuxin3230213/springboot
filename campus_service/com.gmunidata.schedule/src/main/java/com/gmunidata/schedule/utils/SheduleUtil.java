package com.gmunidata.schedule.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SheduleUtil {

    //根据节数将课程表写入
    public static void writeExcel(Workbook workbook, XSSFSheet sheet,List<Map<String,Object>> excelListMap){
        for (Map<String,Object> tempMap:excelListMap) {
            int sunday = Integer.parseInt(String.valueOf(tempMap.get("schedule")).split("|")[0]);
            int startNc =  Integer.parseInt(String.valueOf(tempMap.get("startNc")))+1;
            //强转为String 避免数据在String和Long之间来回跳转
            int nc = Integer.parseInt(String.valueOf(tempMap.get("nc")));
            sheet.addMergedRegion(new CellRangeAddress(startNc,(startNc+nc-1),sunday,sunday));
            String str =sheet.getRow(startNc).getCell(sunday).getStringCellValue();
            //将存的周数转换为数组
            String[] s = String.valueOf(tempMap.get("num")).split(",");
             int [] week = new int[s.length];
             //将string数组转换为int数组
            for (int i = 0; i < week.length ; i++) {
                week[i] = Integer.parseInt(s[i]);
            }
            //排序
            Arrays.sort(week);
            List<String> s1 =new ArrayList<>();
            int start = week[0];
            if (week.length==1){
                s1.add(String.valueOf(start));
            }else{
            for (int i = 0; i < week.length-1 ; i++) {
                if (week[i+1]-week[i]!=1){
                    if (start==week[i]){
                        s1.add(String.valueOf(start));
                    }else{
                        s1.add(start+"-"+week[i]);
                    }
                    start=week[i+1];
                }

            }
            if (start==week[week.length-1]){
                s1.add(String.valueOf(start));
            }else {
                s1.add(start+"-"+week[week.length-1]);
            }
            }

            String weeks = "";
            for (int i = 0; i <s1.size() ; i++) {
                weeks += s1.get(i)+",";
            }
            XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
            style.setWrapText(true);
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                String[] value =((String)tempMap.get("schedule")).split("\\|");
                sheet.getRow(startNc).getCell(sunday).setCellStyle(style);
                if ("".equals(str) || str == null){
                    sheet.getRow(startNc).getCell(sunday).setCellValue(value[2]+"\n"+"{第"+weeks.substring(0,weeks.length()-1)+"周}"+"\n"+value[3]+"\n"+value[4]+"\n");
                }else{
                    //如果各自内的数据不为空，则去除比较开始周数的大小，小的再前，大的灾后
                    if ( Integer.parseInt(str.split("\\n")[1].substring(2,3)) < Integer.parseInt(weeks.substring(0,1))){
                       sheet.getRow(startNc).getCell(sunday).setCellValue(str+value[2]+"\n"+"{第"+weeks.substring(0,weeks.length()-1)+"周}"+"\n"+value[3]+"\n"+value[4]+"\n");
                    }else {
                        sheet.getRow(startNc).getCell(sunday).setCellValue(value[2] + "\n" + "{第" + weeks.substring(0, weeks.length() - 1) + "周}" + "\n" + value[3] + "\n" + value[4] + "\n" + str);
                    }
                }
        }
    }

    //获取所有合并单元格(返回又有合并单元格的列的组合的字符串)
    public static String  getAllCell(Sheet sheet,int startLine , int startCul){
        int num = sheet.getNumMergedRegions();
        String nc = "";
        for (int i = 0; i < num ; i++) {
            String nc1 ="";
            CellRangeAddress cell = sheet.getMergedRegion(i);
            if (cell.getFirstRow() == startLine && cell.getFirstColumn() == startCul){
                  int lastRow = cell.getLastRow();
                  if (lastRow > 11){
                      lastRow = 11;
                  }
                for (int j = startLine; j <= lastRow ; j++) {
                    nc1 += (j - 1)+",";
                }
            }
            nc = nc1;
            if (nc!=""){
                if (nc.length() == 1){
                    return nc;
                }else{
                    return nc.substring(0,nc.length()-1);
                }
            }
        }
        return String.valueOf(startLine-1);
    }

    //解析传入的周数
    public static String getWeek(String week){
        String  s ="";
        try{
        String weeks = week.substring(2,week.length()-2);
        String[] strs = weeks.split(",");
        for (int i = 0; i <strs.length ; i++) {
            String[] str1 = strs[i].split("-");
            String str ="";
            if (str1.length==1){
                s += (str1+",");
            }else{
                for (int j = Integer.parseInt(str1[0]); j <= Integer.parseInt(str1[1]); j++) {
                   s += j+",";
                }

            }
        }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return s.substring(0,s.length()-1);


    }

}
