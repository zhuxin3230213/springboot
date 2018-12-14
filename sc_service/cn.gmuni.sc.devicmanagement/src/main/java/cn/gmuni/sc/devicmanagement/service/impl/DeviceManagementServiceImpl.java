package cn.gmuni.sc.devicmanagement.service.impl;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.devicmanagement.mapper.DeviceManagementMapper;
import cn.gmuni.sc.devicmanagement.model.Device;
import cn.gmuni.sc.devicmanagement.service.IDeviceManagementService;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceManagementServiceImpl implements IDeviceManagementService {

    @Autowired
    DeviceManagementMapper managementMapper;

    @Override
    public Content addDevice(Device device) {
        Content con = new Content();
        int num =managementMapper.checkCode(device);
        if (num != 0){
            con.setFlag(1);
            con.setMessage("设备编码重复或此连接已存在！");
            return con;
        }
        device.setId(IdGenerator.getId());
       int f = managementMapper.addDevice(device) > 0 ? 0 : 1;
       con.setFlag(f);
       if (f == 0){
           con.setMessage("添加成功！");
       }else {
           con.setMessage("添加失败！");
       }

        return con;
    }

    @Override
    public Content updateDevice(Device device) {
        Content con = new Content();
        int num =managementMapper.checkCode(device);
        if (num != 0){
            con.setFlag(1);
            con.setMessage("设备编码重复或此连接已存在！");
            return con;
        }
        int f = managementMapper.updateDevice(device) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            con.setMessage("修改成功！");
        }else {
            con.setMessage("修改失败！");
        }
        return con;
    }

    @Override
    public Content delDevice(List<String> ids) {
        Content con = new Content();
        int f = managementMapper.delDevice(ids) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            con.setMessage("删除成功");
        }else {
            con.setMessage("删除失败");
        }
        return con;
    }

    @Override
    public List<Map<String, Object>> listDevice(Map<String, Object> params) {
        return managementMapper.listDevice(params);
    }
}
