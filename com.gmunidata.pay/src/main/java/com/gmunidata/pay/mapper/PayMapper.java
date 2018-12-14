package com.gmunidata.pay.mapper;

import com.gmunidata.pay.model.ChangeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface PayMapper {
    int save(Map<String, String> param);
    List<Map<String, String>> list(String name);

    void saveChange(ChangeModel model);

    Date getMaxTime(@Param("name") String name);

    void deleteLog(@Param("id") String id);
}
