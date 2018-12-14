package cn.gmuni.sc.devicmanagement.mapper;

import cn.gmuni.sc.devicmanagement.model.Device;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface DeviceManagementMapper {
    //新增设备
    int addDevice(Device device);

    //修改设备
    int updateDevice(Device device);

    //删除设备
    int delDevice(List<String> ids);

    //查询设备信息
    List<Map<String,Object>> listDevice(Map<String,Object> params);

    //校验设备id唯一
     int checkCode(Device device);

}
