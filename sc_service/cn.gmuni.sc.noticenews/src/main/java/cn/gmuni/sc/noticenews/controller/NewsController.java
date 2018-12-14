package cn.gmuni.sc.noticenews.controller;


import cn.gmuni.sc.http.http.HttpAPIService;
import cn.gmuni.sc.http.remote.ICampusEndService;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.log.utils.SysLogger;
import cn.gmuni.sc.utils.DateUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static cn.gmuni.sc.utils.httputils.TemplateConfig.springTemplateEngine;


/**
 * @Author: zhuxin
 * @Date: 2018/9/6 18:10
 * @Description:
 */
@Controller
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    HttpAPIService httpAPIService;


    @Autowired
    @Qualifier("campusEndService")
    ICampusEndService iCampusEndService;


    //通过id查询new对象
    @RequestMapping("/newsDetail/{id}")
    public String listNews(@PathVariable("id") String id, Model model, HttpServletRequest request) {

        String url =  iCampusEndService.getUrl("/news/newsDtetail/" + id);
        try {
            Object news = iCampusEndService.request(url);
            Map<String, Object> mp = stringtoMap(news.toString());//获取数据
            Map<String,Object> data = (Map<String, Object>) mp.get("data");

            //content解析
            String newContent = String.valueOf(data.get("content"));
            SpringTemplateEngine engine = springTemplateEngine();
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            WebContext context = new WebContext(requestAttributes.getRequest(), requestAttributes.getResponse(), requestAttributes.getRequest().getServletContext());
            context.setVariable("imagePath", "");
            data.put("content", engine.process(newContent, context));
            Object updateTime = data.get("updateTime");
            if(updateTime!=null && !"".equals(updateTime)){
                data.put("updateTime", DateUtils.date2String(DateUtils.long2Date((Long) updateTime),DateUtils.COMMON_DATETIME));
            }
            model.addAttribute("news", data);

        } catch (Exception e) {
            e.printStackTrace();
            SysLogger.error(e,SysLogModule.NOTICE_LOG,SysLogType.VISIT_LOG);
        }
        SysLogger.info("新闻通知查询",SysLogModule.NOTICE_LOG,SysLogType.VISIT_LOG);

        return "news_detail";
    }


    private Map<String, Object> stringtoMap(String model) {
        JSONObject json = JSONObject.parseObject(model);
        Map<String, Object> map = json.toJavaObject(Map.class);
        return map;
    }

}
