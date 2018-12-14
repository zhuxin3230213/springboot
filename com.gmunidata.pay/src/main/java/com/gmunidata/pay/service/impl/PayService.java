package com.gmunidata.pay.service.impl;

import com.gmunidata.pay.mapper.PayMapper;
import com.gmunidata.pay.model.ChangeModel;
import com.gmunidata.pay.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PayService implements IPayService {

    @Autowired
    PayMapper mapper;
    @Override
    public void saveLog(Map<String, String> param) {
        mapper.save(param);
    }

    @Override
    public List<Map<String, String>> list(String name) {
        return mapper.list(name);
    }

    @Override
    public void saveChange(ChangeModel model)
    {
        mapper.saveChange(model);
    }

    @Override
    public Date getMaxTime(String name) {
        return mapper.getMaxTime(name);
    }

    @Override
    public void deleteLog(String id) {
        mapper.deleteLog(id);
    }
}
