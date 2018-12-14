package cn.gmuni.zsgl.controller;

import cn.gmuni.zsgl.cache.FooterCache;
import cn.gmuni.zsgl.cache.HeaderCache;
import cn.gmuni.zsgl.service.ConfigService;
import cn.gmuni.zsgl.service.VisitStatisticsService;
import cn.gmuni.zsgl.util.Result;
import cn.gmuni.zsgl.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:ZhuXin
 * @Description: 招生系统配置信息控制层
 * @Date:Create in 17:13 2018/5/7
 * @Modified By:
 **/
@Api(value = "配置信息接口类", description = "配置信息服务API根目录")
@Controller
public class ConfigController {

    @Autowired
    @Qualifier("configServiceImpl")
    ConfigService configService;

    @Autowired
    @Qualifier("visitStatisticsServiceImpl")
    VisitStatisticsService visitStatisticsService;


    @ApiOperation(value = "列表信息", notes = "")
    @GetMapping(value = "/configs")
    public String listConfigs(Model model, HttpServletRequest request) {
        model.addAttribute("listConfigs", configService.listConfigs());
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "contactUs.html", "联系我们");
        return "view/contactUs";
    }


    /**
     * 查询所有系统配置信息
     *
     * @return
     */
    @GetMapping(value = "/config")
    @ResponseBody
    public Result findAll() {
        return ResultUtil.success(configService.listConfigsByCode());
    }

}
