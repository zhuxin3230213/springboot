package testspringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import testspringboot.util.StringToDateConverter;

import javax.annotation.PostConstruct;

/**
 * @Author:ZhuXin
 * @Description:日期转换配置
 * @Date:Create in 16:11 2018/5/4
 * @Modified By:
 **/

@Configuration
public class WebConfigBeans {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     *增加字符串转日期的功能
     */
    @PostConstruct
    public  void initEditableValidation(){
        ConfigurableWebBindingInitializer initializer =(ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if(initializer.getConversionService() != null){
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new StringToDateConverter());
         }
    }


}
