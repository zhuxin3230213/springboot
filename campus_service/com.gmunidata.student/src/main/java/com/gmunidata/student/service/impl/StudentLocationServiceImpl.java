package com.gmunidata.student.service.impl;

import cn.gmuni.utils.IdGenerator;
import com.github.pagehelper.PageHelper;
import com.gmunidata.student.mapper.StudentLocationMapper;
import com.gmunidata.student.model.StudentLocation;
import com.gmunidata.student.service.StudentLocationService;
import com.gmunidata.utils.DateUtils;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create 2018/11/29 11:14
 * @Modified By:
 **/
@Service
public class StudentLocationServiceImpl implements StudentLocationService {

    @Autowired
    StudentLocationMapper studentLocationMapper;


    @Override
    public Map<String, Object> add(Map<String, String> params) {
        StudentLocation studentLocation = new StudentLocation();
        studentLocation.setId(IdGenerator.getId());
        studentLocation.setCreateTime(new Date());
        studentLocation.setUserInfo(params.get("userInfo"));
        studentLocation.setSchool(params.get("school"));
        studentLocation.setStudentId(params.get("studentId"));
        Date gprsTime = DateUtils.long2Date(Long.parseLong(params.get("gprsTime")));
        //创建日期格式化对象   因为DateFormat类为抽象类
        DateFormat bf = new SimpleDateFormat(DateUtils.COMMON_DATETIME);//多态
        studentLocation.setGprsTime(bf.format(gprsTime));
        studentLocation.setLongitude(params.get("longitude"));
        studentLocation.setLatitude(params.get("latitude"));
        studentLocation.setAltitude(params.get("altitude"));
        studentLocation.setCity(params.get("city"));
        studentLocation.setAddress(params.get("address"));

        studentLocation.setStuSignTime(params.get("stuSignTime"));
        int i = studentLocationMapper.add(studentLocation);
        Map<String, Object> map = new HashMap<>();
        if (i > 0) {
            map.put("stuSignTime", params.get("stuSignTime"));
            map.put("address", params.get("address"));
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> list(Map<String, String> params) {
        // PageHelper.startPage(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));
        String currDate = DateUtils.getCurrDate(DateUtils.LONG_DATE);
        Map<String, String> map = new HashMap<>();
        map.put("userInfo", params.get("userInfo"));
        map.put("school", params.get("school"));
        map.put("studentId", params.get("studentId"));
        map.put("stuSignTime", currDate);
        return studentLocationMapper.list(map);
    }

    //根据学号获取学生最新位置信息
    @Override
    public StudentLocation getStu(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("studentId", params.get("studentId"));
        return studentLocationMapper.getStu(map);
    }

    @Override
    public List<Map<String, Object>> addressList(Map<String, Object> params) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("studentId", String.valueOf(params.get("studentCode")));
        return studentLocationMapper.addressList(queryParams);
    }

}
