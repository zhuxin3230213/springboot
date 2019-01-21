package com.gmunidata.schoolbus.mapper;

import com.gmunidata.schoolbus.model.SchoolBus;
import com.gmunidata.schoolbus.model.SchoolBusDriver;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SchoolBusDriverMapper {

    //新增校车司机信息
    int addSchoolBusDriver(SchoolBusDriver busDriver);

    //编辑校车司机信息
    int updateSchoolBusDriver(SchoolBusDriver busDriver);

    //删除校车司机信息
    int delSchoolBusDriver(List<String> ids);

    //查询校车司机管理列表
    List<SchoolBusDriver> listSchoolBusDriver(Map<String,Object> params);

    //校验Code唯一
    int checkSchoolBusDriverCode(SchoolBusDriver busDriver);
}
