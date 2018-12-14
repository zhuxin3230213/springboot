package com.gmunidata.student.mapper;

import com.gmunidata.student.model.StudentLocation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create 2018/11/29 10:34
 * @Modified By:
 **/
@Service
@Mapper
public interface StudentLocationMapper {

    int add(StudentLocation studentLocation);

    int delete(String id);

    int update(StudentLocation studentLocation);

    StudentLocation getStu(Map<String, Object> params);

    List<Map<String,Object>> list(Map<String, String> params);

    List<Map<String,Object>> addressList(Map<String,String> params);

}
