package com.example.ibatis.controller;

import com.example.ibatis.model.Config;
import com.example.ibatis.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 联系我们控制类
 */

@RestController
@RequestMapping("/co")
public class ConfigController {


    @Autowired
    @Qualifier("configServiceImpl")
    ConfigService configService;


    @GetMapping("/list")
    public List<Config> listConfig(){
    return configService.listConfigs();
    }

}
