package com.gmunidata.schoolbus.service;

import com.gmunidata.base.response.Content;
import com.gmunidata.schoolbus.model.SchoolBus;

import java.util.List;
import java.util.Map;

public interface SchoolBusService {
    //新增校车信息
    Content addSchoolBus(SchoolBus bus);

    //编辑校车信息
    Content updateSchoolBus(SchoolBus bus);

    //删除校车信息
    Content delSchoolBus(List<String> ids);

    //查询校车管理列表
    List<SchoolBus> listSchoolBus(Map<String,Object> params);

}
