package com.gmunidata.schoolbus.service.impl;

import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.gmunidata.base.response.Content;
import com.gmunidata.schoolbus.mapper.SchoolBusMapper;
import com.gmunidata.schoolbus.model.SchoolBus;
import com.gmunidata.schoolbus.service.SchoolBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SchoolBusServiceImpl  implements SchoolBusService {

    @Autowired
    SchoolBusMapper mapper;

    @Override
    public Content addSchoolBus(SchoolBus bus) {
        Content con = new Content();
        int num = mapper.checkSchoolBusCode(bus);
        if (num > 0){
            con.setFlag(1);
            con.setMessage("该工牌号已存在");
            return con;
        }
        bus.setId(IdGenerator.getId());
        bus.setCreateTime(new Date());
        int f = mapper.addSchoolBus(bus) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("添加成功");
        }else {
            con.setMessage("添加失败");
        }
        return con;
    }

    @Override
    public Content updateSchoolBus(SchoolBus bus) {
        Content con = new Content();
        int f = mapper.updateSchoolBus(bus) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("修改成功");
        }else {
            con.setMessage("修改失败");
        }
        return con;
    }

    @Override
    public Content delSchoolBus(List<String> ids) {
        Content con = new Content();
        int f = mapper.delSchoolBus(ids) > 0 ? 0 : 1;
        con.setFlag(f);
        if (f == 0) {
            con.setMessage("删除成功");
        }else {
            con.setMessage("删除失败");
        }
        return con;
    }

    @Override
    public List<SchoolBus> listSchoolBus(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(), page.getSize());
        return mapper.listSchoolBus(params);
    }
}
