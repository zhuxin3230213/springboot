package com.gmunidata.utils;

import cn.gmuni.utils.IdGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {
    static Map<String, Integer> map;
    public static final String basePath =System.getProperty("java.io.tmpdir");


    //将列转换为数字
    public static int cellStringToInt(String s) {
        List list = Arrays.asList(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"});
        int sum = 0;
        String[] str = s.split("");
        for (int i = 0; i < str.length; i++) {
            int idx = list.indexOf(str[i]) + 1;
            for (int j = i; j < str.length - 1; j++) {
                idx = (idx * 26);
            }
            sum += idx;
        }
        return sum;
    }

    //传入字段自动生成excel行
    public static XSSFRow autoRowX(List<String> lists, XSSFRow row ){
        Cell cell =null;
        if(lists.size()!=0){
            for (int i = 0; i <lists.size() ; i++) {
                cell = row.createCell((short) i);
                cell.setCellValue(lists.get(i));
            }
        }
        return  row;
    }

    //下载文件，关闭流
    public static void downExcel(HttpServletResponse res,XSSFWorkbook workbook){
    OutputStream os = null;
        try {
        os = res.getOutputStream();
        workbook.write(os);
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }


   //返回数字有的字段
    public static Map<Integer,String>  excelHead(Sheet sheet,int startLine,int startClu,List<String> listName){
         Cell cell = null;
        Map<Integer,String> map = new HashMap<>();
        try{
            //获取excel总共有多少行和多少列数据
            int allClu = sheet.getRow(startLine).getPhysicalNumberOfCells();
            //计算要插入的数据总共有多少行，多少列
            Row row =sheet.getRow(startLine);

        for (int i = startClu; i < allClu ; i++) {
            cell =row.getCell(i);
            String str = cell.getStringCellValue();
            for (String s:listName) {
                if(str.equals(s)){
                    map.put(i ,s);
                }
            }

         }
        }catch (Exception e){
            map.clear();
            return map;
        }
         return map;
    }

    //计算行数


    //校验excelCell数据格式
    public static String resCell(Cell cell){
        String  value = null;
        if(cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:

                    value = cell.getStringCellValue();
                    if ("".equals(value)){
                        value = null;
                    }
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        } else {
                            value = "";
                        }
                    } else {
                        value = new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // 导入时如果为公式生成的数据则无值
                    if (!cell.getStringCellValue().equals("")) {
                        value = cell.getStringCellValue();
                    } else {
                        value = cell.getNumericCellValue() + "";
                    }
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                case Cell.CELL_TYPE_ERROR:
                    value = "";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                    break;
                default:
                    break;

            }
        }
        return value;
    }

    //保存文件
    public static String saveFile(MultipartFile file,String lastName) {
        String  uuid =IdGenerator.getId()+lastName;
        String path = basePath + File.separator + uuid;
        try {
            File tempFile = new File(basePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            File newFile = new File(path);
            if (newFile.exists()) {
                newFile.delete();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    //删除文件
    public static void deleteFile(String uuid){
            try {
                FileUtils.deleteQuietly(new File(basePath+File.separator+uuid));
            }catch (Exception e){


        }
    }

    //导出模板
    public static XSSFWorkbook modelOut(HttpServletResponse res,String fileName) {
        InputStream is = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        try {
            Resource resource = new ClassPathResource(fileName);
            is =resource.getInputStream();;// 将excel文件转为输入流
            workbook = new XSSFWorkbook(is);// 创建个workbook，
            // 获取第一个sheet
            sheet = workbook.getSheetAt(0);
            res.setContentType("UTF-8");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null ){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    //校验是否没有必填列
    public static String checkBi(Map<Integer,String> map,String[] strBi){
        List<String> nameBi = Arrays.asList(strBi);
        String s = null;
        for (int i = 0; i < nameBi.size() ; i++) {
            boolean f = false;
            for (Integer name: map.keySet()) {
                if(nameBi.get(i).equals(map.get(name))){
                    f =true;
                }

            }
            if (f == false){
                s += nameBi.get(i)+",";
            }
        }
        return s;
    }

    //传入文件名和sheet页名称 返回sheet页
    public static Sheet getSheet(String fileName,String name){
        Workbook workbook = null;
        Sheet  sheet = null;
        try {
            if (fileName.substring(fileName.indexOf(".")).equals(".xls")) {
                workbook = new HSSFWorkbook(new FileInputStream(ExcelUtil.basePath + File.separator + fileName));
                sheet = workbook.getSheet(name);
            } else if (fileName.substring(fileName.indexOf(".")).equals(".xlsx")){
                workbook = new XSSFWorkbook(new FileInputStream(ExcelUtil.basePath + File.separator + fileName));
                sheet = workbook.getSheet(name);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return  sheet;
    }

}