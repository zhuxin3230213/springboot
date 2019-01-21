package cn.gmuni.sc.utils.httputils;

import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * @Author:ZhuXin
 * @Description: thymeleaf 字符串模板解析
 * @Date:Create in 14:52 2018/6/27
 * @Modified By:
 **/


public class TemplateConfig {


    public static SpringTemplateEngine springTemplateEngine() {
        //字符串模版引擎对象
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

        //内置方言
        IDialect springStandardDialect = new SpringStandardDialect();
        springTemplateEngine.setDialect(springStandardDialect);

        //字符串解析器
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();

        //使用缓存
        stringTemplateResolver.setCacheable(true);
        stringTemplateResolver.setTemplateMode(TemplateMode.HTML);

        springTemplateEngine.setTemplateResolver(stringTemplateResolver);

        return springTemplateEngine;

    }

}
