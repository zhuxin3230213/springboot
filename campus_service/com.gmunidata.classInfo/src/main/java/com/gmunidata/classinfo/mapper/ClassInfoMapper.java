package com.gmunidata.classinfo.mapper;

import com.gmunidata.classinfo.model.ClassInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
@Mapper
public interface ClassInfoMapper {

     int saveClass(ClassInfo classInfo);

     int editClass(ClassInfo classInfo);

     int deleteClass(List<String> ids);

     List<Map<String, Object>> listClass(Map<String, String> params);

     List<Map<String, Object>> getClass(Map<String, String> params);

     List<ClassInfo> outClass();

     long checkCodeAndNum(ClassInfo classInfo);

     long checkStudent(List<String> ids);

     long checkSchedule(List<String> ids);

     //创建临时表
     void createTemp(String tabTempName);

     //清楚原表中数据
     void delAll();

     //删除临时表
     void dropTemp(String tabTempName);

     //往临时表中批量插入数据
     int listAddTemp(String tabTempName,List<ClassInfo> lists);

     //往原表中批量插入数据
     int listAddClass(List<ClassInfo> lists);

     //覆盖（筛选数据）
     List<ClassInfo> tempAll(String tabTempName);

     //追加（筛选数据）
     List<ClassInfo> tempHalf(String tabTempName);

     //查询name重复
     List<String> selectName(String tabTempName);

     //查询code重复
     List<String> selectCode(String tabTempName);

     //导入的数据和数据库对比重复数据
     List<String> checkName(String tabTempName);

     //导入的数据和数据库对比重复数据
     List<String> checkCode(String tabTempName);

     //覆盖（删除)
     int deleteReptName(List<String> lists);

     //覆盖（删除)
     int deleteReptCode(List<String> lists);
}
