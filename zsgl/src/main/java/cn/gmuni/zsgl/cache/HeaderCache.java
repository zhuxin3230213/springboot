package cn.gmuni.zsgl.cache;

import cn.gmuni.zsgl.service.MenuService;
import cn.gmuni.zsgl.service.impl.MenuServiceImpl;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.gmuni.zsgl.util.TemplateConfig.springTemplateEngine;

/**
 * @Author:ZhuXin
 * @Description:
 * 菜单栏固定处理
 * @Date:Create in 15:41 2018/7/26
 * @Modified By:
 **/

@Component
public class HeaderCache {

    public static final String path ="templates/templates/header.html";
    private static String headerHtml = "";
    private static List<Map<String, Object>> menus = new ArrayList<>();
    private static String template = "";
    private static HeaderCache cache = null;


    private HeaderCache(){}

    @Autowired
    @Qualifier("menuServiceImpl")
    private MenuService menuService;


    /**
     * 初始化菜单层级
     */
    public  void initCache(){
        getTemplate();
        initMenuData();
    }

    //io获取模版内容
    public  void getTemplate(){
        try {
            String str = null;
            Resource resource = new ClassPathResource(path);
            BufferedReader  in = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            while ((str = in.readLine()) != null) {
                template += str;
            }
            in.close();
        }catch (Exception e){
          e.printStackTrace();
        }

    }

    //获取菜单数据
    public  void initMenuData(){
        if(menuService == null){
            menuService = (MenuServiceImpl) SpringBootUtil.getBean("menuServiceImpl");
        }
        menus  = menuService.menuTree("1");
    }

    //渲染模板
    private  void renderTemplate(){
        SpringTemplateEngine engine = springTemplateEngine();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        WebContext context = new WebContext(requestAttributes.getRequest(),requestAttributes.getResponse(),requestAttributes.getRequest().getServletContext());
        Map<String,Object> map = new HashMap<>();
        map.put("listMenus",menus);
        context.setVariables(map);
        headerHtml = engine.process(template, context);
    }

    /**
     * 更新缓存时调用
     */
    public static void  updateCache(){
        cache.initMenuData();
        headerHtml="";
    }


    /**
     * 返回菜单列表
     * @return
     */
    public static String getHeaderHtml(){
        if("".equals(headerHtml)){
            cache.renderTemplate();
        }
        return headerHtml;
    }

    /**
     * Spring实例化该Bean之后马上执行此方法
     */
    @PostConstruct
    public void init(){
        cache = new HeaderCache();
        initCache();
    }
}
