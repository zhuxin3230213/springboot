package com.gmunidata.student.mapper;


import com.gmunidata.student.model.LoginInfo;
import com.gmunidata.student.model.Student;
import com.gmunidata.student.model.StudentFamily;
import com.gmunidata.student.model.StudentResume;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StudentMapper {


      int addStudent(Student student);

      int addStudentFamily(List<StudentFamily> studentFamilys);

      int addStudentResume(List<StudentResume> studentResumes);

      int updateStudent(Student student);

      int updateStudentFamily(StudentFamily studentFamily);

      int updateStudentResume(StudentResume studentResume);

      int delStudent(List<String> list);

      int delStudentFam(List<String> list);

      int delStudentRes(List<String> list);

      //根据索要删除的idcha查询学生的code
      List<String> selectCodeById(List<String> list);
      //根据code删除所有的用户登录
      int deleteLogin(List<String> list);

      List<Student> listStudent(Map<String, String> params);

      long cheakCode(Map<String, Object> params);

      List<StudentFamily> listFam(String studentId);

      List<StudentResume> listRes(String studentId);

      List<Map<String,String>> getdeptName(List<Map<String, String>> list);

      List<Student> outStudent();

      //创建临时表
      void createTemp(String tbTempName);

      //清楚原表中数据
      void delAll();

      //清楚原表中数据
      void delLoginAll();

      //删除重复的表中的数据
      int deleteLoginReptCode(List<String> lists);

      //删除临时表
      void dropTemp(String tbTempName);

      //往临时表中批量插入数据
      int listAddTemp(@Param("tableName") String tableName,@Param("list") List<Student> list);

      //往原表中批量插入数据
      int listAddStudent(List<Student> list);

      //覆盖（筛选数据）
      List<Student> tempAll(String tbTempName);

      //追加（筛选数据）
      List<Student> tempHalf(String tbTempName);

      //查询编码重复
      List<String> selectRepet(String tbTempName);

      //查询与表中的编码重复
      List<String> checkTabCode(String tbTempName);

      //删除重复的表中的数据
      int deleteReptCode(List<String> lists);


      //添加登陆信息
      int addLoginInfo(LoginInfo info);

      //批量插入添加登陆信息
      int addLogInfoUser(List<LoginInfo> infos);

      //登陆查询
      LoginInfo getByCode(@Param("code") String code);

      //根据编码修改密码
      int updatePwdByCode(LoginInfo loginInfo);

      //根据编码获取身份证号
      String getIdentityByCode(String code);

}
