package cn.gmuni.zsgl.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author:ZhuXin
 * @Description: SpringBoot之普通类获取Spring容器中的bean
 * 普通类调用spring bean对象
 * 说明：
 * 此类需要放到启动类的同包或者子包下才能被扫描，否则失效
 * @Date:Create in 11:22 2018/5/7
 * @Modified By:
 **/

@Component
public class SpringBootUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBootUtil.applicationContext == null) {
            SpringBootUtil.applicationContext = applicationContext;
        }
        System.out.println("_____________________");
        System.out.println("============ApplicationContext配置成功，在普通类可以通过调用SpringUtils.getApplicationContext()获取aoolicationContext对象," +
                "applicationContext=" + SpringBootUtil.applicationContext + "=============");
        System.out.println("_________________________");

    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取Bean
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}

