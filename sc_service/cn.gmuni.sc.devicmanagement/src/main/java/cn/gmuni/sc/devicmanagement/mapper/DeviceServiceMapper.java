package cn.gmuni.sc.devicmanagement.mapper;

import cn.gmuni.sc.devicmanagement.model.DeviceServiceBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DeviceServiceMapper {
    //新增学校设备服务器
    int addDeviceService(DeviceServiceBean deviceServiceBean);

    //修改学校设备服务器
    int updateDeviceService(DeviceServiceBean deviceServiceBean);

    //删除学校设备服务器
    int delDeviceService(List<String> ids);

    //查询学校设备服务器信息
    List<Map<String,Object>> listDeviceService(Map<String, Object> params);

    //校验服务器设备Url唯一
     int checkUrl(DeviceServiceBean deviceServiceBean);

}
