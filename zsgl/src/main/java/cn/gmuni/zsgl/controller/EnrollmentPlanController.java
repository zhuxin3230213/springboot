package cn.gmuni.zsgl.controller;

import cn.gmuni.zsgl.cache.FooterCache;
import cn.gmuni.zsgl.cache.HeaderCache;
import cn.gmuni.zsgl.entity.Menu;
import cn.gmuni.zsgl.service.*;
import cn.gmuni.zsgl.util.Result;
import cn.gmuni.zsgl.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * @Author:ZhuXin
 * @Description: 招生计划控制层
 * @Date:Create in 16:08 2018/6/4
 * @Modified By:
 **/
@Api(value = "计划接口类", description = "计划服务API根目录")
@Controller
@RequestMapping("/history")
public class EnrollmentPlanController {


    @Autowired
    @Qualifier(value = "yearlyScoreServiceImpl")
    YearlyScoreService yearlyScoreService;

    @Autowired
    @Qualifier(value = "enrollmentPlanServiceImpl")
    EnrollmentPlanService enrollmentPlanService;

    @Autowired
    @Qualifier(value = "menuServiceImpl")
    MenuService menuService;

    @Autowired
    @Qualifier(value = "contentServiceImpl")
    ContentService contentService;

    @Autowired
    @Qualifier("visitStatisticsServiceImpl")
    VisitStatisticsService visitStatisticsService;

    @Value("${postNewsRegulation.currentPage}")
    private Integer pnrCurrentPage;
    @Value("${postNewsRegulation.pageSize}")
    private Integer pnrPageSize;

    @Value("${host.pageSize}")
    private Integer hostPageSize;

    @Value(value = "${plan.provinces}")
    String provinces;

    @Value("${hots.facMenuCode}")
    private String facMenuCode;

    /**
     * 获取招生计划列表
     *
     * @param menuCode
     * @param model
     * @return
     */
    @ApiOperation(value = "获取招生计划或者分数列表", notes = "")
    @GetMapping("/plan/{subModuleId}")
    public String getPlanOrScore(@PathVariable("subModuleId") String menuCode, Model model, HttpServletRequest request) {
        model.addAttribute("hots", contentService.getByLimitHosts(facMenuCode, pnrCurrentPage, hostPageSize));
        model.addAttribute("provinces", enrollmentPlanService.listProvinces());
        Menu menu = menuService.getMenuByCode(menuCode);
        //获取当前年份的前一年
        Integer year = null;
        Calendar a = Calendar.getInstance();
        year = a.get(Calendar.YEAR) - 1;
        if ("2001".equals(menu.getCode())) {
            model.addAttribute("menu", menu);
            model.addAttribute("historyPlanOrScore", enrollmentPlanService.listEnPlansByYearAndProvinces(year, provinces));
            visitStatisticsService.add(request, "historyPlanOrScore",
                    menu.getName());

        }
        if ("2002".equals(menu.getCode())) {
            model.addAttribute("menu", menu);
            model.addAttribute("historyPlanOrScore", yearlyScoreService.listByYearAndProvinces(year, provinces));
            visitStatisticsService.add(request, "historyPlanOrScore",
                    menu.getName());
        }
        model.addAttribute("header", HeaderCache.getHeaderHtml());
        model.addAttribute("footer", FooterCache.getFooterHtml());
        return "view/historyPlanOrScore";
    }


    /**
     * 根据年份和省份获取招生计划
     *
     * @param year
     * @param provinces
     * @return
     */
    @ApiOperation(value = "根据年份和省份、菜单code获取计划", notes = "")
    @GetMapping(value = "/getByYAP")
    @ResponseBody
    public Result getPlanByYearAndProvinces(@RequestParam("yearID") Integer year, @RequestParam("areaId") String provinces,
                                            @RequestParam("menuCode") String menuCode, HttpServletRequest request) {

        Result result = null;
        if ("2001".equals(menuCode)) {
            result = ResultUtil.success(enrollmentPlanService.listEnPlansByYearAndProvinces(year, provinces));
            visitStatisticsService.add(request, "historyPlanOrScore",
                    menuService.getMenuByCode(menuCode).getName());
        }
        if ("2002".equals(menuCode)) {
            result = ResultUtil.success(yearlyScoreService.listByYearAndProvinces(year, provinces));
            visitStatisticsService.add(request, "historyPlanOrScore",
                    menuService.getMenuByCode(menuCode).getName());
        }
        return result;
    }

}


