package com.gmunidata.classinfo.service.impl;

import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.classinfo.mapper.ClassInfoMapper;
import com.gmunidata.classinfo.model.ClassInfo;
import com.gmunidata.classinfo.service.IClassInfoService;
import com.gmunidata.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;


@Service
public class ClassInfoServiceImp implements IClassInfoService {

    private static final   String[] CLASS_CLU = {"班级名称","班级编码","所属专业","入学年份","所属届数","班级号","班主任"};
    private static final  String[] CLASS_CLU_BI = {"班级名称","班级编码","所属专业","入学年份","所属届数","班级号"};

    @Autowired
    ClassInfoMapper mapper;

    @Override
    public Content saveClass(ClassInfo classInfo) {
        Content con = new Content();
        if (classInfo.getName()==null || "".equals(classInfo.getName()) ||classInfo.getCode()==null || "".equals(classInfo.getCode())){
            con.setFlag(1);
            con.setMessage("名称和编码不能为空！");
            return con;
        }
       long num = mapper.checkCodeAndNum(classInfo);
       int f = 1;
       if(num == 0){
           classInfo.setId(IdGenerator.getId());
           classInfo.setCreateTime(new Date());
           f = mapper.saveClass(classInfo) == 1 ? 0 : 1;
       }else{
           con.setFlag(f);
           con.setMessage("添加失败，名称或编码重复！");
           return con;
       }
       con.setFlag(f);
       if(f==0){
           con.setMessage("添加成功！");
           con.setContent(classInfo);
       }else{
           con.setMessage("添加失败！");
       }
       return con;

    }

    @Override
    public Content editClass(ClassInfo classInfo) {
        Content con = new Content();
        long num = mapper.checkCodeAndNum(classInfo);
        List<String> ids = new ArrayList<>();
        ids.add(classInfo.getId());
        long students =mapper.checkStudent(ids);
        long schedules =mapper.checkSchedule(ids);
        if (students != 0 || schedules != 0){
            con.setFlag(1);
            con.setMessage("该班级下存在学生或课程表，无法修改！");
            return con;
        }
        int f = 1;
        if (num == 0) {
            f = mapper.editClass(classInfo) == 1 ? 0 : 1;
        }else{
            con.setFlag(f);
            con.setMessage("编辑失败，名称或编码重复！");
            return con;
        }

        con.setFlag(f);
        if(f==0){
            con.setMessage("编辑成功！");
            con.setContent(classInfo);
        }else{
            con.setMessage("编辑失败！");
        }
        return con;
    }

    @Override
    public Map<String, Object> deleteClass(String classId) {
        Map<String,Object> map =new HashMap<>();
        List<String> ids = Arrays.asList(classId.split(","));
        long students =mapper.checkStudent(ids);
        long schedules =mapper.checkSchedule(ids);
        if (students>0 || schedules>0){
               map.put("flag",false);
               map.put("msg","存在下属学生或课表，无法直接删除");
               return  map;
        }
        int num = mapper.deleteClass(ids);
        map.put("flag", num > 0 ? true : false);
        return map;
    }


    @Override
    public PageInfo<Map<String, Object>> listClass(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        List<Map<String, Object>> map = mapper.listClass(params);
        return  new PageInfo<>(mapper.listClass(params));
    }

    @Override
    public List<Map<String, Object>> getClass(Map<String, String> params) {
        return mapper.getClass(params);
    }


    @Override
    public void  outClass(HttpServletResponse res) {
            String fileName = "class.xlsx";
            XSSFWorkbook workbook = ExcelUtil.modelOut(res,fileName);
            XSSFSheet sheet = workbook.getSheet("Sheet0");
            XSSFRow row = sheet.createRow( 0);
            XSSFCell cell = null;
            //第一行设置样式
            List<String> nameList =Arrays.asList(CLASS_CLU);
            ExcelUtil.autoRowX(nameList,row);
            List<ClassInfo> lists = mapper.outClass();
            int i = 1;
            for (ClassInfo classInfo : lists) {
                i++;
                XSSFRow row1 = sheet.createRow(i);
                cell = row1.createCell(0);
                cell.setCellValue(classInfo.getName());
                cell = row1.createCell(1);
                cell.setCellValue(classInfo.getCode());
                cell = row1.createCell(2);
                cell.setCellValue(classInfo.getSubjectName());
                cell = row1.createCell(3);
                cell.setCellValue(classInfo.getYear());
                cell = row1.createCell(4);
                cell.setCellValue(classInfo.getPeriod());
                cell = row1.createCell(5);
                cell.setCellValue(classInfo.getClassNo());
                cell = row1.createCell(6);
                cell.setCellValue(classInfo.getHeadTeacher());
                row1.setHeight((short) 400);
            }

           ExcelUtil.downExcel(res,workbook);
        }


    @Override
    public void  modelClass(HttpServletResponse res) {
        String fileName = "class.xlsx";
        XSSFWorkbook workbook = ExcelUtil.modelOut(res,fileName);
        ExcelUtil.downExcel(res,workbook);
    }


    @Override
    public Content inClass(Map<String, Object> params) {
        Content con =new Content();
        //起始行和起始列（转换成java运算机制）
        int startLine = (int) params.get("startLine")-1;
        int startClu = ExcelUtil.cellStringToInt((String) params.get("startClu")) - 1;
        List<String> listName =Arrays.asList(CLASS_CLU);
        String fileName = (String) params.get("uuid");
        String name = (String) params.get("name");
        //获取sheet页
        Sheet  sheet = ExcelUtil.getSheet(fileName,name);
        if (sheet == null){
            con.setFlag(1);
            con.setMessage("不是Excel文件！");
        }

        int allLine = sheet.getLastRowNum();
        if (allLine >= 1000){
            con.setFlag(1);
            con.setMessage("班级导入请确保数据小于1000条数据！");
            return con;
        }

        //map接收  excel中的名称和所在行数
        Map<Integer, String> map = ExcelUtil.excelHead(sheet, startLine, startClu, listName);
        //校验比填列是否存在
          String strb =  ExcelUtil.checkBi(map,CLASS_CLU_BI);
            if (strb != null){
                con.setFlag(1);
                con.setMessage("必填列"+strb.substring(4,strb.length()-1)+"列不存在！");
                return con;
                }
            List<ClassInfo> listClass =excelIn(sheet,startLine,startClu,map);
            List<ClassInfo> tempClass = null;
            String str = checkNumFormat(listClass);
             if (str != null){
                 con.setFlag(1);
                 String[] s = str.split(",");
                 con.setMessage("第"+((int) params.get("startLine")+Integer.parseInt(s[0]))+"行,"+s[1]+"列"+s[2]);
                 return con;
               }
        String tabTempName = "sc_"+IdGenerator.getId();
        try {
                        //创建临时表
                        mapper.createTemp(tabTempName);
                        //给临时表中添加数据
                        mapper.listAddTemp(tabTempName,listClass);
                        //查询临时表中是否存在相同的那么和code
                        List<String> names = mapper.selectName(tabTempName);
                        List<String> codes = mapper.selectCode(tabTempName);
                        if(names.size() == 0 && codes.size() == 0){
                        if((int)params.get("addOrCover") == 0){
                            //筛选数据
                            tempClass = mapper.tempAll(tabTempName);
                            //判断临时表中筛选的数据是否和导入的数据一致
                            if (tempClass.size() == listClass.size()){
                                //判断输入的所属专业是否存在（存在则返回null）
                            String str1 = checkSubjectId(tempClass);
                            if (str1 == null) {
                                //将选数据表清空
                                 mapper.delAll();
                                //将数据插入表中
                                int m = mapper.listAddClass(tempClass);
                                if (m == tempClass.size()) {
                                    con.setFlag(0);
                                    con.setMessage("操作成功！");
                                    return con;
                                }
                            }else{
                                con.setFlag(1);
                                String[] s = str1.split(",");
                                con.setMessage("第"+((int) params.get("startLine")+Integer.parseInt(s[0]))+"行,"+s[1]+"列"+s[2]);
                                return con;
                            }
                            }else {
                                //判断数据被筛选掉了
                                if (tempClass.size() == 0){
                                    con.setFlag(1);
                                    con.setMessage("可能因为编码/名称重复或所属专业不存在导致无法插入！" );
                                    return con;
                                }
                            }
                        }else if((int)params.get("addOrCover")==1) {
                            //筛选数据
                            tempClass = mapper.tempHalf(tabTempName);
                            //校验所属专业名称是否存在
                            String str1 = checkSubjectId(tempClass);
                            if (str1 == null) {

                            if ((tempClass.size()!= listClass.size())){
                                //有重复数据（不覆盖的）
                                names = mapper.checkName(tabTempName);
                                codes = mapper.checkCode(tabTempName);
                                if ((int)params.get("merge") == 0){
                                 String s =nameAndCode(names,codes);
                                 con.setFlag(2);
                                 con.setMessage("文件中"+s+"和已有的班级名称和编码存在重复，无法导入！");
                                 return con;
                                }else if((int)params.get("merge") == 1){
                                    //有重复数据（覆盖）
                                    mapper.deleteReptName(names);
                                    mapper.deleteReptCode(codes);
                                    tempClass = mapper.tempHalf(tabTempName);
                                    int m = mapper.listAddClass(tempClass);
                                    con.setFlag(0);
                                    con.setMessage("操作成功！");
                                    return con;
                                }
                            }else {
                                //将数据插入表中
                                 mapper.listAddClass(tempClass);
                                con.setFlag(0);
                                con.setMessage("操作成功！");
                                return con;
                               }
                            }else {
                                con.setFlag(1);
                                String[] s = str1.split(",");
                                con.setMessage("第"+((int) params.get("startLine")+Integer.parseInt(s[0]))+"行,"+s[1]+"列"+s[2]);
                                return con;
                               }

                          }
                        }else {
                                String s =nameAndCode(names,codes);
                                con.setFlag(1);
                                con.setMessage("文件中"+s+"和已有的班级名称和编码存在重复，无法导入！");
                                return con;
                           }
                    }catch (Exception e){
                        e.printStackTrace();
                        con.setFlag(1);
                        con.setMessage("导入失败！");
                        return con;
                    }finally {
                        //删除临时表
                        mapper.dropTemp(tabTempName);
                        if (con.getFlag() != 2){
                            ExcelUtil.deleteFile(ExcelUtil.basePath + File.separator + fileName);
                        }
                    }
        return con;
       }

    /**
     * 将excel数据封装进对象中
     * @param sheet
     * @param startLine
     * @param startClu
     * @param map
     * @return
     */
    public List<ClassInfo> excelIn(Sheet sheet,int startLine,int startClu, Map<Integer,String> map){
        //获取excel总共有多少行和多少列数据
        int allLine = sheet.getLastRowNum();
        int allClu = sheet.getRow(startLine).getPhysicalNumberOfCells();
        Row row = null;
        List<ClassInfo> listClass = new ArrayList<>();
        for (int i = startLine+1; i <= allLine ; i++) {
            row = sheet.getRow(i);
            ClassInfo classInfo = new ClassInfo();
            for (int j = startClu; j <allClu ; j++) {
                String value = ExcelUtil.resCell(row.getCell(j));
                switch (map.get(j)){
                    case "班级名称":
                        classInfo.setName(value);
                        break;
                    case "班级编码":
                        classInfo.setCode(value);
                        break;
                    case "所属专业":
                        classInfo.setSubjectName(value);
                        break;
                    case "入学年份":
                        classInfo.setYear(value);
                        break;
                    case "所属届数":
                        classInfo.setPeriod(value);
                        break;
                    case "班级号":
                        classInfo.setClassNo(value);
                        break;
                    case "班主任":
                        classInfo.setHeadTeacher(value);
                        break;
                    default:
                        break;
                }
            }

            classInfo.setId(IdGenerator.getId());
            classInfo.setCreateTime(new Date());
            listClass.add(classInfo);
        }
        return listClass;
    }

    private String  checkNumFormat(List<ClassInfo> classInfos){

        for (int i = 0; i < classInfos.size() ; i++) {
            if (classInfos.get(i).getYear() == null ){
                return (i+1)+",入学年份"+",数据不能为空";
            }else{
                if(classInfos.get(i).getYear().matches("[0-9]{1,}") ==false){
                    return (i+1)+",入学年份"+",数据应为纯数字";
                }
            }
            if (classInfos.get(i).getSubjectName() == null){
                return (i+1)+",所属专业"+",数据不能为空";
            }
            if (classInfos.get(i).getClassNo() == null ){
                return (i+1)+",班级号"+",数据不能为空";
            }
            if (classInfos.get(i).getName() == null){
                return (i+1)+",班级名称"+",数据不能为空";
            }
            if (classInfos.get(i).getCode() == null){
                return (i+1)+",班级编码"+",数据不能为空";
            }
            if (classInfos.get(i).getPeriod() == null){
                   return (i+1)+",所属届数"+",数据不能为空";
            }else{
                if(classInfos.get(i).getPeriod().matches("[0-9]{1,}") ==false){
                    return (i+1)+",所属届数"+",数据应为纯数字";
                }
            }
        }
        return null;
    }

    private String  checkSubjectId (List<ClassInfo> tempClass){
        for (int i = 0; i < tempClass.size() ; i++) {
            if (tempClass.get(i).getSubjectId() == null){
                return (i+1)+",所属专业"+",无此专业请先维护此专业";
            }
        }
        return null;
    }

    private String nameAndCode(List<String> names,List<String> codes){
        String s = "名称：";
        String s1 = "班级编码：";
        if (names.size()!=0){
            for (String name:names) {
                s += name +",";
            }
        }
        if (codes.size()!=0){
            for (String code:codes) {
                s1 += code +",";
            }
      }
    return s+s1;
   }


}






