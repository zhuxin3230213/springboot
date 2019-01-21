package com.gmunidata.schoolbus.service;

import com.gmunidata.base.response.Content;
import com.gmunidata.schoolbus.model.SchoolBusDriver;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface SchoolBusDriverService {

    //新增校车司机信息
    Content addSchoolBusDriver(SchoolBusDriver busDriver);

    //编辑校车司机信息
    Content updateSchoolBusDriver(SchoolBusDriver busDriver);

    //删除校车司机信息
    Content delSchoolBusDriver(List<String> ids);

    //查询校车司机管理列表
    List<SchoolBusDriver> listSchoolBusDriver(Map<String,Object> params);
}
