package cn.gmuni.zsgl.controller;

import cn.gmuni.zsgl.service.MenuService;
import cn.gmuni.zsgl.service.VisitStatisticsService;

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


/**
 * @Author:ZhuXin
 * @Description: 招生系统一级菜单与二级菜单控制层
 * @Date:Create in 17:12 2018/5/7
 * @Modified By:
 **/
@Api(value = "菜单接口类", description = "菜单服务API根目录")
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    @Qualifier("menuServiceImpl")
    MenuService menuService;

    @Autowired
    @Qualifier("visitStatisticsServiceImpl")
    VisitStatisticsService visitStatisticsService;

    @Value("${menu.status}")
    private String menuStatus;

    /**
     * 菜单层级获取
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "菜单层级获取", notes = "")
    @GetMapping(value = "/index.html")
    public String findByMenu(Model model, HttpServletRequest request) {
        model.addAttribute("listMenus", menuService.menuTree(menuStatus));
        return "index";
    }

    /**
     * 异步刷新菜单列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "异步刷新菜单列表", notes = "")
    @GetMapping(value = "/listMenus")
    public Result listMenus(HttpServletRequest request) {
        return ResultUtil.success(menuService.menuTree(menuStatus));
    }

}
