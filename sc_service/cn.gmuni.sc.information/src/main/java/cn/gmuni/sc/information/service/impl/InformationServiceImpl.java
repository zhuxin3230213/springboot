package cn.gmuni.sc.information.service.impl;

import cn.gmuni.sc.information.mapper.InformationMapper;
import cn.gmuni.sc.information.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InformationServiceImpl implements InformationService {


    @Autowired
    InformationMapper informationMapper;


    @Override
    public Map<String, Object> findIndentifier(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("studentId", params.get("studentId"));
        map.put("school", params.get("school"));
        return informationMapper.findUserInfoByStudentIdAndSchool(map);
    }
}
