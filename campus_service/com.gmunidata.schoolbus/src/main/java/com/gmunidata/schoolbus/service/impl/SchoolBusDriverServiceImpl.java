package com.gmunidata.schoolbus.service.impl;

import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.gmunidata.base.response.Content;
import com.gmunidata.schoolbus.mapper.SchoolBusDriverMapper;
import com.gmunidata.schoolbus.model.SchoolBusDriver;
import com.gmunidata.schoolbus.service.SchoolBusDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SchoolBusDriverServiceImpl implements SchoolBusDriverService {

    @Autowired
    SchoolBusDriverMapper driverMapper;

    @Override
    public Content addSchoolBusDriver(SchoolBusDriver busDriver) {
        Content con = new Content();
       int num = driverMapper.checkSchoolBusDriverCode(busDriver);
       if (num > 0){
           con.setFlag(1);
           con.setMessage("该车牌号已存在");
           return con;
       }
        busDriver.setId(IdGenerator.getId());
        int f = driverMapper.addSchoolBusDriver(busDriver) > 0 ? 0 : 1;
       con.setFlag(f);
        if (f == 0) {
            con.setMessage("添加成功");
        }else {
            con.setMessage("添加失败");
        }
            return con;
    }

    @Override
    public Content updateSchoolBusDriver(SchoolBusDriver busDriver) {
        Content con = new Content();
        int f = driverMapper.updateSchoolBusDriver(busDriver) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("修改成功");
        }else {
            con.setMessage("修改失败");
        }
        return con;
    }

    @Override
    public Content delSchoolBusDriver(List<String> ids) {
        Content con = new Content();
        int f = driverMapper.delSchoolBusDriver(ids) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("删除成功");
        }else {
            con.setMessage("删除失败");
        }
        return con;
    }

    @Override
    public List<SchoolBusDriver> listSchoolBusDriver(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return driverMapper.listSchoolBusDriver(params);
    }
}
