package cn.gmuni.sc.devicmanagement.service.impl;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.blower.mapper.BlowerMapper;
import cn.gmuni.sc.blower.service.impl.BlowerServiceImpl;
import cn.gmuni.sc.devicmanagement.mapper.DeviceManagementMapper;
import cn.gmuni.sc.devicmanagement.model.Device;
import cn.gmuni.sc.devicmanagement.model.Repair;
import cn.gmuni.sc.devicmanagement.service.IDeviceManagementService;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceManagementServiceImpl implements IDeviceManagementService {

    @Autowired
    DeviceManagementMapper managementMapper;

    @Autowired
    BlowerMapper blowerMapper;

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

    @Override
    public Map<String,Object> repair(Repair repair) {
        Map<String,Object> map = new HashMap<>();
        repair.setId(IdGenerator.getId());
        int f =  managementMapper.repair(repair)> 0 ? 0 : 1;
        if (f == 0){
            Map<String,Object> params = new HashMap<>();
            params.put("blowerCode",repair.getDeviceCode());
            params.put("status",0);
            params.put("schoolCode",UserUtils.getLoginUser().getSchool());
            blowerMapper.updateBlowerStatus(params);
            map.put("flag",true);
            map.put("message","报修成功");
        }else {
            map.put("flag",false);
            map.put("message","报修失败");
        }
        return map;
    }

    @Override
    public Content maintenance(Repair repair) {
        Content con = new Content();
        int f = managementMapper.maintenance(repair) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            Map<String,Object> params = new HashMap<>();
            params.put("blowerCode",repair.getDeviceCode());
            params.put("schoolCode",UserUtils.getLoginUser().getSchool());
            blowerMapper.initStatus(params);
            con.setMessage("已维护");
        }else {
            con.setMessage("维护失败");
        }
        return con;
    }
}
