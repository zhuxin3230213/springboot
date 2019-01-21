package com.gmunidata.student.service;

import com.gmunidata.student.model.StudentLocation;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create 2018/11/29 11:14
 * @Modified By:
 **/
public interface StudentLocationService {


    Map<String,Object> add(Map<String,String> params);

    List<Map<String,Object>> list(Map<String,String> params);

    StudentLocation getStu(Map<String,Object> params);

    List<Map<String,Object>> addressList(Map<String,Object> params);
}
