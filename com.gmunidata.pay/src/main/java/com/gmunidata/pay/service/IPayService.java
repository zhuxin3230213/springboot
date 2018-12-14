package com.gmunidata.pay.service;

import com.gmunidata.pay.model.ChangeModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IPayService {
    void saveLog(Map<String, String> param);
    List<Map<String, String>> list(String name);

    void saveChange(ChangeModel model);

    Date getMaxTime(String name);

    void deleteLog(String id);
}
