package cn.gmuni.portal.resolver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class TemplateEngineConfig extends WebMvcConfigurerAdapter {


    @Resource(name="thymeleafViewResolver")
    private ThymeleafViewResolver thymeleafViewResolver;

    //运行环境
    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        if (thymeleafViewResolver != null) {
            Map<String, Object> vars = new HashMap<>(8);
            //运行环境
            vars.put("profilesActive",active);
            thymeleafViewResolver.setStaticVariables(vars);
        }
        super.configureViewResolvers(registry);
    }


}
