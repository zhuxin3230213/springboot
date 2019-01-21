package cn.gmuni.sc.devicmanagement.service.impl;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.devicmanagement.mapper.DeviceServiceMapper;
import cn.gmuni.sc.devicmanagement.model.DeviceServiceBean;
import cn.gmuni.sc.devicmanagement.service.IDeviceService;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    DeviceServiceMapper mapper;

    @Override
    public Content addDeviceService(DeviceServiceBean deviceServiceBean) {
        Content con = new Content();
        int num =mapper.checkUrl(deviceServiceBean);
        if (num != 0){
            con.setFlag(1);
            con.setMessage("该设备的url地址已存在！");
            return con;
        }
        deviceServiceBean.setId(IdGenerator.getId());
        int f = mapper.addDeviceService(deviceServiceBean) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            con.setMessage("添加成功！");
        }else {
            con.setMessage("添加失败！");
        }

        return con;
    }

    @Override
    public Content updateDeviceService(DeviceServiceBean deviceServiceBean) {
        Content con = new Content();
        int num =mapper.checkUrl(deviceServiceBean);
        if (num != 0){
            con.setFlag(1);
            con.setMessage("该设备的url地址已存在！");
            return con;
        }
        int f = mapper.updateDeviceService(deviceServiceBean) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            con.setMessage("修改成功！");
        }else {
            con.setMessage("修改失败！");
        }

        return con;
    }

    @Override
    public Content delDeviceService(List<String> ids) {
        Content con = new Content();
        int f = mapper.delDeviceService(ids) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0){
            con.setMessage("删除成功");
        }else {
            con.setMessage("删除失败");
        }
        return con;
    }

    @Override
    public List<Map<String, Object>> listDeviceService(Map<String, Object> params) {
        return mapper.listDeviceService(params);
    }
}
