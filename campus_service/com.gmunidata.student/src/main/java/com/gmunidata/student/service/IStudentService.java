package com.gmunidata.student.service;

import com.gmunidata.base.response.Content;
import com.gmunidata.student.model.LoginInfo;
import com.gmunidata.student.model.Student;
import com.gmunidata.student.model.StudentFamily;
import com.gmunidata.student.model.StudentResume;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IStudentService {

    /**
     * 添加学生信息
     * @param studMap
     * @param listFam
     * @param listRes
     * @return
     */
     Content addOrUpStudent(Map<String, Object> studMap, List<Map<String, Object>> listFam, List<Map<String, Object>> listRes);

    /**
     * 删除学生信息
     * @param stuIds
     * @return
     */
     Content delStudent(String stuIds);


    /**
     * 查询学生信息
     * @param params
     * @return
     */
     List<Student> listStudent(Map<String, String> params);

    /**
     * 查询学生家庭成员信息
     * @param params
     * @return
     */
     List<StudentFamily>  listFam(Map<String, String> params);

    /**
     * 查询学生教育经历信息
     * @param params
     * @return
     */
     List<StudentResume>  listRes(Map<String, String> params);

    /**
     * 导出学生信息
     * @param response
     */
    void  outStudent(HttpServletResponse response);

    /**
     * 导出蹙额生信息模板
     * @param response
     */
    void  modelStudent(HttpServletResponse response);

    /**
     * 导入学生信息
     * @param params
     * @return
     */
    Content  inStudent(Map<String,Object> params);

    /**
     * 修改学生登录的登录的密码
     * @param code
     * @return
     */
     Content updatePwdByCode(String code);
}
