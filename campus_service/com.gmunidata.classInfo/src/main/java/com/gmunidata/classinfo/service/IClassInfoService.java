package com.gmunidata.classinfo.service;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.classinfo.model.ClassInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IClassInfoService {

    /**
     * 添加班级
     * @param classInfo
     * @return
     */
     Content saveClass(ClassInfo classInfo);

    /**
     * 编辑班级信息
     * @param classInfo
     * @return
     */
     Content editClass(ClassInfo classInfo);

    /**
     * 删除班级
     * @param classId
     * @return
     */
     Map<String,Object> deleteClass(String classId);


    /**
     * 查询
     * @param params
     * @return
     */
     PageInfo<Map<String, Object>> listClass(Map<String, String> params);

    /**
     * 查询所有班级（不分页）
     * @param params
     * @return
     */
     List<Map<String, Object>> getClass(Map<String, String> params);

    /**
     * 导出查询
     * @return
     */
     void  outClass(HttpServletResponse response);
    /**
     * 导出模板
     * @return
     */
    void  modelClass(HttpServletResponse response);

    /**
     * 导入班级
     * @return
     */
    Content  inClass(Map<String, Object> params);




}
