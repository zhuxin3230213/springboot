package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.entity.Config;
import cn.gmuni.zsgl.service.ConfigService;
import cn.gmuni.zsgl.util.ConfigUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


import java.util.*;

import static cn.gmuni.zsgl.util.TemplateConfig.springTemplateEngine;


/**
 * @Author:ZhuXin
 * @Description:
 * ConfigServiceImplTest
 * @Date:Create in 10:30 2018/6/26
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigServiceImplTest {


    @Autowired
    @Qualifier("configServiceImpl")
    ConfigService configService;

    /**
     * 文本中图片转译
     */
    @Test
    public void test(){
        SpringTemplateEngine engine = springTemplateEngine();
        String str = "<img src=\"/upload/download-image/0a7cc942b37841f098d6ffdb2273522a\" style=\"max-width:100%;\" alt=\"123\" title=\"123\" " +
                "th:attr=\"src=${imagePath}+'/0a7cc942b37841f098d6ffdb2273522a'\">";
        Context context = new Context();
        context.setVariable("imagePath","http://127.0.0.1:8081/imagePath");
        System.out.println(engine.process(str,context));
    }


    /**
     * 根据code获取常用链接
     * code： gmuni_contact_us_urls
     */
    @Test
    public void getConfigByCode(){
        Config gmuniContactUsUrls = configService.getConfigByCode("gmuni_contact_us_urls");
        System.out.println(gmuniContactUsUrls.getValue());
    }


    /**
     * 获取底部配置信息列表
     */
    @Test
    public void listConfigs() {
        List<Config> configs = configService.listConfigs();
       /*for (Config temp:configs){
           System.out.println(temp.toString());
       }*/

        for (int i = 0; i < configs.size(); i++) {
            System.out.println(configs.get(i));
        }
       
    }


    /**
     * 获取所需配置信息
     */
    @Test
    public void listConfig(){
        ConfigUtil configUtil = configService.listConfigsByCode();
        System.out.println(configUtil);


    }
}