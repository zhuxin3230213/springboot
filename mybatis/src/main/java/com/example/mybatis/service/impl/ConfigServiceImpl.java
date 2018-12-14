package com.example.mybatis.service.impl;

import com.example.mybatis.mapper.ConfigMapper;
import com.example.mybatis.model.Config;
import com.example.mybatis.service.ConfigService;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ConfigServiceImpl implements ConfigService {


    @Autowired
    ConfigMapper configMapper;

    @Override
    public List<Config> listConfigs() {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        return configMapper.listConfigs();
    }


    @Async
    public void dataTranslate(int i) {
        System.out.println("启动了线程" + i);
    }
}
