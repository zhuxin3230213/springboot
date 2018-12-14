package com.example.mybatis.service;

import com.example.mybatis.model.Config;

import java.util.List;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create in 17:54 2018/8/1
 * @Modified By:
 **/


public interface ConfigService {

    List<Config> listConfigs();
    public void dataTranslate(int i);
}
