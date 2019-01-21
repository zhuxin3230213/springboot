package com.gmunidata.student.service.impl;

import cn.gmuni.lookup.container.LookupCache;
import cn.gmuni.lookup.model.LookupTree;
import cn.gmuni.utils.DesUtils;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.Md5Util;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.gmunidata.base.response.Content;
import com.gmunidata.student.mapper.StudentMapper;
import com.gmunidata.student.model.LoginInfo;
import com.gmunidata.student.model.Student;
import com.gmunidata.student.model.StudentFamily;
import com.gmunidata.student.model.StudentResume;
import com.gmunidata.student.service.IStudentService;
import com.gmunidata.utils.DateUtils;
import com.gmunidata.utils.ExcelUtil;
import com.gmunidata.utils.LookUpUtil;
import com.gmunidata.utils.MapToBeanUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class StudentServiceImp implements IStudentService {

    @Autowired
    StudentMapper studentMapper;

    private static final String[] STUDENT_CLU ={"姓名","学号","所属院系","所属专业","所属班级","性别","政治面貌","学生状态","民族","入学方式","学制","出生日期",
            "身份证号","入学时间","毕业时间","家庭住址","家庭电话","个人电话","个人邮箱","其他党派","毕业学校","学习形式","类别形式","招生类型","是否港澳台侨外"};
    private static final String[] STUDENT_CLU_BI ={"姓名","学号","所属院系","所属专业","所属班级","性别","政治面貌","学生状态","民族","入学方式","学制","出生日期",
            "身份证号","入学时间","毕业时间","个人电话","学习形式","类别形式","招生类型"};

    @Override
    public Content addOrUpStudent(Map<String, Object> studMap, List<Map<String, Object>> listFam, List<Map<String, Object>> listRes) {

        Content con = new Content();
        Student student = new Student();
        StudentFamily studentFamily = new StudentFamily();
        StudentResume studentResume = new StudentResume();
        List<StudentFamily> studentFamilys =new ArrayList<>();
        List<StudentResume> studentResumes =new ArrayList<>();
        int num1 = 2;
        long num = 0;
        try {
            student = (Student) MapToBeanUtil.mapToObject(studMap, student.getClass());
            num = studentMapper.cheakCode(studMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num == 0) {
            if (student.getId() == null ||"".equals(student.getId())) {
                student.setId(IdGenerator.getId());
                num1 = studentMapper.addStudent(student) == 1 ? 0 : 1;
                if (num1 == 0) {
                    //添加登陆信息
                    LoginInfo loginInfo = new LoginInfo();
                    loginInfo.setId(IdGenerator.getId());
                    //密码为身份证后6位X转为0
                    String identity = student.getIdentity();
                    String pwd = identity.substring(identity.length() - 6).replace("x","0").replace("X","0");
                    loginInfo.setLoginPwd(Md5Util.encode(pwd));
                    loginInfo.setStudentCode(student.getCode());
                    loginInfo.setStatus("1");
                    studentMapper.addLoginInfo(loginInfo);
                    if (listFam.size() != 0) {
                        for (Map<String, Object> listfam : listFam) {
                            try {
                                studentFamily = (StudentFamily) MapToBeanUtil.mapToObject(listfam, studentFamily.getClass());
                                studentFamily.setId(IdGenerator.getId());
                                studentFamily.setStudentId(student.getId());
                                studentFamilys.add(studentFamily);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        studentMapper.addStudentFamily(studentFamilys);
                    }
                    if (listRes.size() != 0) {
                        for (Map<String, Object> lisres : listRes) {
                            try {
                                studentResume = (StudentResume) MapToBeanUtil.mapToObject(lisres, studentResume.getClass());
                                studentResume.setId(IdGenerator.getId());
                                studentResume.setStudentId(student.getId());
                                studentResumes.add(studentResume);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        studentMapper.addStudentResume(studentResumes);
                    }
                }
            } else {
                num1 = studentMapper.updateStudent(student) == 1 ? 0 : 1;
                if (num1 == 0) {
                    if (listFam.size() != 0) {
                        for (Map<String, Object> lisfam : listFam) {
                            try {
                                studentFamily = (StudentFamily) MapToBeanUtil.mapToObject(lisfam, studentFamily.getClass());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (studentFamily.getId() == null || "".equals(studentFamily.getId())) {
                                studentFamily.setId(IdGenerator.getId());
                                studentFamily.setStudentId(student.getId());
                                studentFamilys.add(studentFamily);
                            } else {
                                studentMapper.updateStudentFamily(studentFamily);
                            }
                        }
                        if(studentFamilys.size()>0){
                            studentMapper.addStudentFamily(studentFamilys);
                        }

                    }
                    if (listRes.size() != 0) {
                        for (Map<String, Object> lisres : listRes) {

                            try {
                                studentResume = (StudentResume) MapToBeanUtil.mapToObject(lisres, studentResume.getClass());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (studentResume.getId() == null || "".equals(studentResume.getId())) {
                                studentResume.setId(IdGenerator.getId());
                                studentResume.setStudentId(student.getId());
                                studentResumes.add(studentResume);
                            } else {
                                studentMapper.updateStudentResume(studentResume);
                            }
                        }
                         if (studentResumes.size()>0){
                             studentMapper.addStudentResume(studentResumes);
                         }

                    }
                }

            }

            if (num1 == 0) {
                con.setMessage("添加或修改成功！");
            } else if (num1 == 1) {
                con.setMessage("添加或修改失败！");
            }
        } else {
            con.setMessage("该学号已存在，学号不能重复！");
        }
        con.setFlag(num1);
        return con;
    }

    @Override
    public Content delStudent(String stuIds) {
        List<String> list = Arrays.asList(stuIds.split(","));
        Content con = new Content();
        List<String> codes = studentMapper.selectCodeById(list);
        studentMapper.deleteLogin(codes);
        int f = studentMapper.delStudent(list) > 0 ? 0 : 1;
        studentMapper.delStudentFam(list);
        studentMapper.delStudentRes(list);
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("删除成功！");
        } else {
            con.setMessage("删除失败!");
        }
        return con;
    }


    @Override
    public List<Student> listStudent(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return studentMapper.listStudent(params);
    }

    @Override
    public List<StudentFamily> listFam(Map<String, String> params) {
        return studentMapper.listFam(params.get("id"));
    }

    @Override
    public List<StudentResume> listRes(Map<String, String> params) {
        return studentMapper.listRes(params.get("id"));
    }


    @Override
    public void  outStudent(HttpServletResponse res) {
        String fileName = "student.xlsx";
        XSSFWorkbook workbook = ExcelUtil.modelOut(res,fileName);
        XSSFSheet sheet = workbook.getSheet("Sheet0");
        XSSFRow row = sheet.createRow((short) 0);
        XSSFCell cell = null;
        //第一行设置样式
        row.setHeight((short) 400);
        //所需生成的列名
        List<String> nameList =Arrays.asList(STUDENT_CLU);
        ExcelUtil.autoRowX(nameList,row);

        List<Student> lists = studentMapper.outStudent();
        List<LookupTree> list = LookupCache.list();
        Map<String,LookupTree> lookups = new HashMap<>();
        for (LookupTree ca : list) {
            lookups.put(ca.getCode(), ca);
        }
        int i = 1;
        for (Student student : lists) {
            i++;
            XSSFRow row1 = sheet.createRow( i);
            cell = row1.createCell(0);
            cell.setCellValue(student.getName());
            cell = row1.createCell(1);
            cell.setCellValue(student.getCode());
            cell = row1.createCell(2);
            cell.setCellValue(student.getDeptName());
            cell = row1.createCell(3);
            cell.setCellValue(student.getSubjectName());
            cell = row1.createCell(4);
            cell.setCellValue(student.getClassName());
            cell = row1.createCell(5);
            cell.setCellValue(LookUpUtil.codeByName("sex",student.getSex(),lookups));
            cell = row1.createCell(6);
            cell.setCellValue(LookUpUtil.codeByName("politicsStatus",student.getPoliticsStatus(),lookups));
            cell = row1.createCell(7);
            cell.setCellValue(LookUpUtil.codeByName("stuStatus",student.getStatus(),lookups));
            cell = row1.createCell(8);
            cell.setCellValue(LookUpUtil.codeByName("nation",student.getNation(),lookups));
            cell = row1.createCell(9);
            cell.setCellValue(LookUpUtil.codeByName("way",student.getWay(),lookups));
            cell = row1.createCell(10);
            cell.setCellValue(LookUpUtil.codeByName("lc",student.getLc(),lookups));
            cell = row1.createCell(11);
            cell.setCellValue(DateUtils.date2String(student.getBirthday(),DateUtils.LONG_DATE));
            cell = row1.createCell(12);
            cell.setCellValue(student.getIdentity());
            cell = row1.createCell(13);
            cell.setCellValue(DateUtils.date2String(student.geteTime(),DateUtils.LONG_DATE));
            cell = row1.createCell(14);
            cell.setCellValue(DateUtils.date2String(student.getgTime(),DateUtils.LONG_DATE));
            cell = row1.createCell(15);
            cell.setCellValue(student.getAddress());
            cell = row1.createCell(16);
            cell.setCellValue(student.getHomePhone());
            cell = row1.createCell(17);
            cell.setCellValue(student.getPhone());
            cell = row1.createCell(18);
            cell.setCellValue(student.getEmail());
            cell = row1.createCell(19);
            cell.setCellValue(student.getOtherParty());
            cell = row1.createCell(20);
            cell.setCellValue(student.getgSchool());
            cell = row1.createCell(21);
            cell.setCellValue(LookUpUtil.codeByName("type",student.getType(),lookups) );
            cell = row1.createCell(22);
            cell.setCellValue(LookUpUtil.codeByName("categoryType",student.getCategoryType(),lookups) );
            cell = row1.createCell(23);
            cell.setCellValue(LookUpUtil.codeByName("eType",student.geteType(),lookups) );
            cell = row1.createCell(24);
            cell.setCellValue(LookUpUtil.codeByName("hkm",student.getHkm(),lookups) );
            row1.setHeight((short) 400);
        }
        ExcelUtil.downExcel(res,workbook);
    }

    @Override
    public void  modelStudent(HttpServletResponse res) {
        String fileName = "student.xlsx";
        XSSFWorkbook workbook = ExcelUtil.modelOut(res,fileName);
        ExcelUtil.downExcel(res,workbook);
    }


    @Override
    public Content inStudent(Map<String, Object> params) {
        Content con =new Content();
        //起始行和起始列（转换成java运算机制）
        int startLine = (int) params.get("startLine") - 1;
        int startClu = ExcelUtil.cellStringToInt((String) params.get("startClu")) - 1;
        List<String> listName =Arrays.asList(STUDENT_CLU);
        String fileName = (String) params.get("uuid");
        String name =(String) params.get("name");
        Sheet  sheet = ExcelUtil.getSheet(fileName,name);
        //map接收  excel中的名称和所在列数
        Map<Integer, String> map = ExcelUtil.excelHead(sheet, startLine, startClu, listName);
        if (map.size() == 0) {
            con.setFlag(1);
            con.setMessage("所上传中没有模板相应的字段，无法插入！");
            return con;
        }else{
            String s =  ExcelUtil.checkBi(map,STUDENT_CLU_BI);
            if (s != null){
                con.setFlag(1);
                con.setMessage("必填列"+s.substring(4,s.length()-1)+"列不存在！");
                return con;
            }
        }
        //最大行数
        int allLine = sheet.getLastRowNum();
        //判断是否有数据
        if (allLine <= startLine){
            con.setFlag(1);
            con.setMessage("无可导入的数据！");
            return con;
        }
        //一万条数据限制
      /*  if (allLine >= 10000){
            con.setFlag(1);
            con.setMessage("请确保数据小于10000条！");
            return con;
        }*/
        //获取excel中所有要保存的数据
        List<Student> listStudent = excelIn(sheet,startLine,startClu,allLine,map);
        String tableName = "sc_"+IdGenerator.getId();
        //创建临时表
        try{
            //创建临时表
            studentMapper.createTemp(tableName);
            List<Student> listTemp = new ArrayList<>();
            //总共的数据条数
            int size = listStudent.size() - 1;
            for (int i = 0; i <= size ; i++) {
                 listTemp.add(listStudent.get(i));
                //大于一千条插入一次或者当数据读完的时候插入
                if (listTemp.size()%1000 == 0 || (i == size && listTemp.size()%1000 != 0)){
                     studentMapper.listAddTemp(tableName,listTemp);
                     listTemp =new ArrayList<>();
                 }
            }
            String str1 = checkNumFormat(map,listStudent);
                if (str1 != null){
                    con.setFlag(1);
                    String[] s = str1.split(",");
                    con.setMessage("第" + ((int) params.get("startLine") + Integer.parseInt(s[0])) + "行," + s[1] + "列"+s[2]);
                    return con;
                }

            con = insertStudent(params,listStudent,tableName);
        }catch (Exception e){
            e.printStackTrace();
            con = Content.gettError();
            return con;
        }finally {
            studentMapper.dropTemp(tableName);
            if (con.getFlag() != 2) {
                ExcelUtil.deleteFile(ExcelUtil.basePath+File.separator+fileName);
            }
        }
        return con;


    }

    @Override
    public Content updatePwdByCode(String code) {
        Content con =new Content();
        LoginInfo loginInfo =new LoginInfo();
        String identity = studentMapper.getIdentityByCode(code);
        String pwd = identity.substring(identity.length() - 6).replace("x","0").replace("X","0");
        loginInfo.setStudentCode(code);
        loginInfo.setLoginPwd(pwd);
        int m = studentMapper.updatePwdByCode(loginInfo) > 0 ? 0 : 1;
        con.setFlag(1);
        if (m == 0){
            con.setMessage("请求成功！");
        }else{
            con.setMessage("请求失败！");
        }
        return con;
    }

    /**
     * 从临时表往数据库插入数据
     * @param params
     * @return
     */
    private Content insertStudent(Map<String, Object> params,List<Student> listStudent,String tableName) {
        Content con = new Content();
        List<Student> tempStudent = null;
        List<String> codes = studentMapper.selectRepet(tableName);
        if (codes.size() != 0){
            String s =reptCode(codes);
            con.setFlag(1);
            con.setMessage("所需导入的文本中"+s+"存在重复无法导入！");
            return  con;
        }
        if ((int) params.get("addOrCover") == 0) {
            //筛选数据
            tempStudent = studentMapper.tempAll(tableName);
            if (tempStudent.size() == listStudent.size()) {
                String str = checkDept(tempStudent);
                if (str == null) {
                    //将选数据表清空
                    studentMapper.delAll();
                    studentMapper.delLoginAll();
                    //将数据插入表中
                    int m = studentMapper.listAddStudent(tempStudent);
                     addLogin(tempStudent);
                    if (m == tempStudent.size()) {
                        con.setFlag(0);
                        con.setMessage("操作成功！");
                        return con;
                    }
                }else {
                    con.setFlag(1);
                    String[] s = str.split(",");
                    con.setMessage("第" + ((int) params.get("startLine") + Integer.parseInt(s[0])) + "行," + s[1] + "列"+s[2]);
                    return con;
                }
            } else {
                con.setFlag(1);
                con.setMessage("可能因为所属部门或所属专业或所属班级数据异常无法插入！");
                return con;
            }
        } else if ((int) params.get("addOrCover") == 1) {

            //筛选数据
            tempStudent = studentMapper.tempHalf(tableName);
            String str1 = checkDept(tempStudent);
            //校验所属院系，所属专业，所属班级
            if (str1 != null){
                con.setFlag(1);
                String[] s = str1.split(",");
                con.setMessage("第" + ((int) params.get("startLine") + Integer.parseInt(s[0])) + "行," + s[1] + "列"+s[2]);
                return con;
            }
            //校验是否有重复数据
            if (tempStudent.size() != listStudent.size()){
                codes = studentMapper.checkTabCode(tableName);
                if((int)params.get("merge") == 0){
                    String code = reptCode(codes);
                    con.setFlag(2);
                    con.setMessage("所导入的文本中"+code+"与已有的学生学号重复！无法导入");
                    return con;
                }else{
                    //删除重复的编码
                    studentMapper.deleteReptCode(codes);
                    studentMapper.deleteLoginReptCode(codes);
                    //重新查询
                    tempStudent = studentMapper.tempHalf(tableName);
                    //将数据插入表中
                    studentMapper.listAddStudent(tempStudent);
                    addLogin(tempStudent);
                    con.setFlag(0);
                    con.setMessage("操作成功！");
                    return con;
                  }
            }else{
                //将数据插入表中
                studentMapper.listAddStudent(tempStudent);
                addLogin(tempStudent);
                con.setFlag(0);
                con.setMessage("操作成功！");
                return con;
            }
        }
        return  con;
    }

    private void addLogin(List<Student> tempStudent){
        List<LoginInfo> loginInfos = new ArrayList<>();
        LoginInfo loginInfo = null;
        for (Student student:tempStudent) {
            //添加登陆信息
            loginInfo = new LoginInfo();
            loginInfo.setId(IdGenerator.getId());
            //密码为身份证后6位X转为0
            String identity = student.getIdentity();
            String pwd = identity.substring(identity.length() - 6).replace("x","0").replace("X","0");
            loginInfo.setLoginPwd(Md5Util.encode(pwd));
            loginInfo.setStudentCode(student.getCode());
            loginInfo.setStatus("1");
            loginInfos.add(loginInfo);
        }
        studentMapper.addLogInfoUser(loginInfos);
    }


    /**
     *将exxcel数据写入对象
     * @param sheet
     * @param startLine
     * @param startClu
     * @param map
     * @return
     */
    public List<Student> excelIn(Sheet sheet,int startLine,int startClu,int allLine,Map<Integer,String> map){
        //获取excel总共有多少行和多少列数据
        int allClu = sheet.getRow(startLine).getPhysicalNumberOfCells();
        Row row = null;
        List<Student> listStudent = new ArrayList<>();
        List<LookupTree> list = LookupCache.list();
        Map<String,LookupTree> lookups = new HashMap<>();
        for (LookupTree ca : list) {
            lookups.put(ca.getCode(), ca);
        }
        for (int i = startLine+1; i <= allLine ; i++) {
            row = sheet.getRow(i);
            Student student = new Student();
            for (int j = startClu; j < allClu ; j++) {
                String value = ExcelUtil.resCell(row.getCell(j));
                switch (map.get(j)){
                    case "姓名":
                        student.setName(value);
                        break;
                    case "学号":
                        student.setCode(value);
                        break;
                    case "所属院系":
                        student.setDeptName(value);
                        break;
                    case "所属专业":
                        student.setSubjectName(value);
                        break;
                    case "所属班级":
                        student.setClassName(value);
                        break;
                    case "性别":
                        student.setSex(LookUpUtil.nameByCode("sex",value,lookups));
                        break;
                    case "政治面貌":
                        student.setPoliticsStatus(LookUpUtil.nameByCode("politicsStatus",value,lookups));
                        break;
                    case "学生状态":
                        student.setStatus(LookUpUtil.nameByCode("stuStatus",value,lookups));
                        break;
                    case "民族":
                        student.setNation(LookUpUtil.nameByCode("nation",value,lookups));
                        break;
                    case "入学方式":
                        student.setWay(LookUpUtil.nameByCode("way",value,lookups));
                        break;
                    case "学制":
                        student.setLc(LookUpUtil.nameByCode("lc",value,lookups));
                        break;
                    case "出生日期":
                        student.setBirthday(DateUtils.string2Date(value,DateUtils.LONG_DATE));
                        break;
                    case "身份证号":
                        student.setIdentity(value);
                        break;
                    case "入学时间":
                        student.seteTime(DateUtils.string2Date(value,DateUtils.LONG_DATE));
                        break;
                    case "毕业时间":
                        student.setgTime(DateUtils.string2Date(value,DateUtils.LONG_DATE));
                        break;
                    case "家庭住址":
                        student.setAddress(value);
                        break;
                    case "家庭电话":
                        student.setHomePhone(value);
                        break;
                    case "个人电话":
                        student.setPhone(value);
                        break;
                    case "个人邮箱":
                        student.setEmail(value);
                        break;
                    case "其他党派":
                        student.setOtherParty(value);
                        break;
                    case "毕业学校":
                        student.setgSchool(value);
                        break;
                    case "学习形式":
                        student.setType(LookUpUtil.nameByCode("type",value,lookups));
                        break;
                    case "类别形式":
                        student.setCategoryType(LookUpUtil.nameByCode("categoryType",value,lookups));
                        break;
                    case "招生类型":
                        student.seteType(LookUpUtil.nameByCode("eType",value,lookups));
                        break;
                    case "是否港澳台侨外":
                        String str = LookUpUtil.nameByCode("hkm",value,lookups);
                        if ("无".equals(str)){
                            str = (LookUpUtil.nameByCode("hkm","否",lookups));
                        }
                        student.setHkm(str);
                        break;
                    default:
                        break;

                }
            }
            student.setId(IdGenerator.getId());
            listStudent.add(student);
        }
        return listStudent;

    }

    private String  checkNumFormat(Map<Integer, String> maps,List<Student> students){
        Map<String,Integer> map = new HashMap<>();
        for (Integer key :maps.keySet()) {
            map.put(maps.get(key),key);
        }
        for (int i = 0; i < students.size() ; i++) {
            if (students.get(i).getName() == null){
                return (i+1)+",姓名"+",数据不能为空";
            }
            if (students.get(i).getCode() == null){
                return (i+1)+",学号"+",数据不能为空";
            }
            if (students.get(i).getDeptName() == null){
                return (i+1)+",所属院系"+",数据不能为空";
            }
            if (students.get(i).getSubjectName() == null){
                return (i+1)+",所属专业"+",数据不能为空";
            }
            if (students.get(i).getClassName() == null){
                return (i+1)+",所属班级"+",数据不能为空";
            }
            if (students.get(i).getBirthday() == null){
                return (i+1)+",出生日期"+",数据格式不正确或者为空";
            }
            if (students.get(i).getIdentity() == null){
                return (i+1)+",身份证号"+",数据不能为空";
            }else{
                String res = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x))$";
                if (Pattern.matches(res,students.get(i).getIdentity()) == false){
                    return (i+1)+",身份证号"+",数据格式错误";
                }
            }
            if (students.get(i).getPhone() == null){
                return (i+1)+",个人电话"+",数据不能为空";
            }else{
                String regExp = "^((13)|(15)|(18)|(17)|(14))\\d{9}$";
                if (Pattern.matches(regExp,students.get(i).getPhone()) == false){
                    return (i+1)+",个人电话"+",数据格式错误";
                }
            }
            if (students.get(i).geteTime() == null){
                return (i+1)+",入学时间"+",数据格式不正确或者为空";
            }
            if (students.get(i).getgTime() == null){
                return (i+1)+",毕业时间"+",数据格式不正确或者为空";
            }
            if ( students.get(i).getSex() == null){
                return (i+1)+",性别"+",数据不能为空";
            }else if (("无").equals(students.get(i).getSex())){
                return (i+1)+",性别"+",数据格式不正确请先维护数据！";
            }

            if ( students.get(i).getPoliticsStatus() == null){
                return i+",政治面貌"+",数据不能为空";
            }else if (("无").equals(students.get(i).getPoliticsStatus())){
                return (i+1)+",政治面貌"+",数据格式不正确请先维护数据！";
            }

            if ( students.get(i).getStatus() == null){
                return (i+1)+",学生状态"+",数据不能为空";
            }else if (("无").equals(students.get(i).getStatus())){
                return (i+1)+",学生状态"+",数据格式不正确请先维护数据！";
            }

            if ( students.get(i).getNation() == null){
                return (i+1)+",民族"+",数据不能为空";
            }else if (("无").equals(students.get(i).getNation())){
                return (i+1)+",民族"+",数据格式不正确请先维护数据！";
            }

            if (students.get(i).getWay() == null){
                return (i+1)+",入学方式"+",数据不能为空";
            }else if (("无").equals(students.get(i).getWay())){
                return (i+1)+",入学方式"+",数据格式不正确请先维护数据！";
            }

            if ( students.get(i).getLc() == null){
                return (i+1)+",学制"+",数据不能为空";
            }else if (("无").equals(students.get(i).getLc())){
                return (i+1)+",学制"+",数据格式不正确请先维护数据！";
            }

            if ( students.get(i).getType() == null){
                return (i+1)+",学习形式"+",数据不能为空";
            }else if (("无").equals(students.get(i).getType())){
                return (i+1)+",学习形式"+",数据格式不正确请先维护数据！";
            }

            if ( students.get(i).getCategoryType() == null){
                return (i+1)+",类别形式"+",数据不能为空";
            }else if (("无").equals(students.get(i).getCategoryType())){
                return (i+1)+",类别形式"+",数据格式不正确请先维护数据！";
            }

            if ( students.get(i).geteType() == null){
                return (i+1)+",招生类型"+",数据不能为空";
            }else if (("无").equals(students.get(i).geteType())){
                return (i+1)+",招生类型"+",数据格式不正确请先维护数据！";
            }

            if ( students.get(i).getHkm() == null){
                return (i+1)+",是否港澳台侨外"+",数据不能为空";
            }else if (("无").equals(students.get(i).getHkm())){
                return (i+1)+",是否港澳台侨外"+",数据格式不正确请先维护数据！";
            }

        }
        return null;
    }

    //校验所属院系，所属专业，所属班级
    private String  checkDept (List<Student> students){
        for (int i = 0; i < students.size() ; i++) {
            if (students.get(i).getDeptId() == null){
                return (i+1)+",所属院系"+",无此院系请先维护此专业";
            }
            if (students.get(i).getSubjectId() == null){
                return (i+1)+",所属专业"+",无此专业请先维护此专业";
            }
            if (students.get(i).getClassId() == null){
                return (i+1)+",所属班级"+",无此班级请先维护此专业";
            }
        }
        return null;
    }


    //组合重复的信息
    private String reptCode(List<String> codes){
        String  s = "学号:";
        for (String code:codes) {
            s += code +",";
        }
        return s;
    }

}