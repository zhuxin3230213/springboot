package com.example.ibatis.service.impl;


import com.example.ibatis.mapper.ConfigMapper;
import com.example.ibatis.model.Config;
import com.example.ibatis.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConfigServiceImpl implements ConfigService {


    @Autowired
    ConfigMapper configMapper;


    @Override
    public List<Config>  listConfigs() {
        return configMapper.selectAll();
    }
}
