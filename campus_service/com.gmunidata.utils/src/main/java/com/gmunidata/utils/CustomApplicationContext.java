package com.gmunidata.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class CustomApplicationContext implements ApplicationContextAware {
    protected static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {

        return context;
    }
    /**
     * 获取配置属性
     */
    public static String getProp(String key){
        return context.getEnvironment().getProperty(key);
    }
}
