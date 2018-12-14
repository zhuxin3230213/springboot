package cn.gmuni.zsgl.cache;

import cn.gmuni.zsgl.service.ConfigService;
import cn.gmuni.zsgl.service.impl.ConfigServiceImpl;
import cn.gmuni.zsgl.util.ConfigUtil;
import cn.gmuni.zsgl.util.SpringBootUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static cn.gmuni.zsgl.util.TemplateConfig.springTemplateEngine;

/**
 * @Author:ZhuXin
 * @Description:
 * 底部配置信息固定处理
 * @Date:Create in 15:41 2018/7/26
 * @Modified By:
 **/

@Component
public class FooterCache {
    public static final String path ="templates/templates/footer.html";
    private static String footerHtml = "";
    private static ConfigUtil configUtil = new ConfigUtil();
    private static String template = "";
    private static FooterCache cache = null;

    @Autowired
    @Qualifier("configServiceImpl")
    ConfigService configService;

    private FooterCache(){}


    /**
     * 初始化底部配置信息
     */
    public  void initCache(){
        getTemplate();
        initMenuData();
    }

    //获取模版内容
    public  void getTemplate(){
        try {
            Resource resource = new ClassPathResource(path);
            String str = null;
            BufferedReader  in = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            while ((str = in.readLine()) != null) {
                template += str;
            }
            in.close();
        }catch (Exception e){
          e.printStackTrace();
        }

    }

    //获取底部所需配置信息数据
    public  void initMenuData(){
        if (configService == null){
           configService = (ConfigServiceImpl) SpringBootUtil.getBean("configServiceImpl");
        }
        configUtil  = configService.listConfigsByCode();
    }

    //渲染模板
    public  void renderTemplate(){
        SpringTemplateEngine engine = springTemplateEngine();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        WebContext context = new WebContext(requestAttributes.getRequest(),requestAttributes.getResponse(),requestAttributes.getRequest().getServletContext());
        Map<String,Object> map = new HashMap<>();
        map.put("configUtil",configUtil);
        context.setVariables(map);
        footerHtml = engine.process(template, context);
    }

    /**
     * 更新缓存时调用
     */
    public static void updateCache(){
        cache.initMenuData();
        footerHtml = "";
    }


    /**
     * 返回底部配置信息
     * @return
     */
    public static String getFooterHtml(){
        if("".equals(footerHtml)){
            cache.renderTemplate();
        }
        return footerHtml;
    }

    /**
     *Spring实例化该Bean之后马上执行此方法
     */
    @PostConstruct
    public void init(){
        cache = new FooterCache();
        initCache();
    }
}
