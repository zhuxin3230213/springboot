package cn.gmuni.sc.devicmanagement.service;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.devicmanagement.model.Device;

import java.util.List;
import java.util.Map;

public interface IDeviceManagementService {

    //新增设备
    Content addDevice(Device device);

    //修改设备
    Content updateDevice(Device device);

    //删除设备
    Content delDevice(List<String> ids);

    //查询设备信息
    List<Map<String,Object>> listDevice(Map<String,Object> params);
}
