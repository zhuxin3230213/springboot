package com.gmunidata.schoolbus.mapper;

import com.gmunidata.schoolbus.model.SchoolBus;

import java.util.List;
import java.util.Map;

public interface SchoolBusMapper {
    //新增校车信息
    int addSchoolBus(SchoolBus bus);

    //编辑校车信息
    int updateSchoolBus(SchoolBus bus);

    //删除校车信息
    int delSchoolBus(List<String> ids);

    //查询校车管理列表
    List<SchoolBus> listSchoolBus(Map<String,Object> params);

}
