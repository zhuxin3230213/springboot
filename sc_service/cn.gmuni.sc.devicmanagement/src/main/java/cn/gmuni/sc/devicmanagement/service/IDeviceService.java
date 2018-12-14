package cn.gmuni.sc.devicmanagement.service;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.devicmanagement.model.Device;
import cn.gmuni.sc.devicmanagement.model.DeviceServiceBean;

import java.util.List;
import java.util.Map;

public interface IDeviceService {

    //新增学校设备服务器
    Content addDeviceService(DeviceServiceBean deviceServiceBean);

    //修改学校设备服务器
    Content updateDeviceService(DeviceServiceBean deviceServiceBean);

    //删除学校设备服务器
    Content delDeviceService(List<String> ids);

    //查询学校设备服务器信息
    List<Map<String,Object>> listDeviceService(Map<String,Object> params);
}
