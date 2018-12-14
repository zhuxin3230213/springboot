package cn.gmuni.zsgl.controller;

import cn.gmuni.zsgl.cache.FooterCache;
import cn.gmuni.zsgl.cache.HeaderCache;
import cn.gmuni.zsgl.service.FamousTeacherStyleService;
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
 * @Description: 名师风采控制层
 * @Date:Create in 10:19 2018/6/14
 * @Modified By:
 **/

@Api(value = "名师风采接口类", description = "名师风采服务API根目录")
@Controller
@RequestMapping("/fast")
public class FamousTeacherStyleController {

    @Autowired
    @Qualifier("famousTeacherStyleServiceImpl")
    FamousTeacherStyleService famousTeacherStyleService;

    @Autowired
    @Qualifier("visitStatisticsServiceImpl")
    VisitStatisticsService visitStatisticsService;

    @Autowired
    @Qualifier("menuServiceImpl")
    MenuService menuService;

    @Value("${fats.currentPage}")
    private Integer fatsCurrentPage;
    @Value("${fats.pageSize}")
    private Integer fatsPageSize;

    /**
     * 进入名师风采页面
     *
     * @param model
     * @param request
     * @return
     */
    @ApiOperation(value = "进入名师风采页面", notes = "")
    @GetMapping(value = "/tea/{subModuleId}")
    public String listFasts(@PathVariable("subModuleId") String menuCode, Model model, HttpServletRequest request) {
        model.addAttribute("module", menuService.getMenuByCode(menuCode));
        model.addAttribute("listTitle", famousTeacherStyleService.getListTitle());
        model.addAttribute("listHonor", famousTeacherStyleService.getListHonor());
        model.addAttribute("totalPage", famousTeacherStyleService.getFastsPageTotal(fatsCurrentPage, fatsPageSize));
        model.addAttribute("teacherInfos", famousTeacherStyleService.listFatsPage(fatsCurrentPage, fatsPageSize));
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "teacherInfo", "名师风采");
        return "view/teacherInfo";
    }


    /**
     * 根据职称和荣誉进行查询
     * 姓名模糊查询
     *
     * @param title
     * @param honor
     * @param request
     * @return
     */
    @ApiOperation(value = "根据职称和荣誉、姓名进行查询", notes = "")
    @GetMapping(value = "/titleHonor")
    @ResponseBody
    public Result listByTitleAndHonor(@RequestParam("currentPage") Integer currentPage, @RequestParam("title") String title, @RequestParam("honor") String honor,
                                      @RequestParam("name") String name, HttpServletRequest request) {
        if (currentPage == null) {
            return ResultUtil.success(famousTeacherStyleService.listByTitleAndHonorPage(title, honor, name, fatsCurrentPage, fatsPageSize));
        } else {
            return ResultUtil.success(famousTeacherStyleService.listByTitleAndHonorPage(title, honor, name, currentPage, fatsPageSize));
        }
    }

    /**
     * 根据id获取教师详情
     *
     * @param fastId
     * @param model
     * @return
     */
    @ApiOperation(value = "获取教师详情", notes = "")
    @GetMapping(value = "/teacherDetail/{fastId}")
    public String teacherDetail(@PathVariable("fastId") String fastId, Model model, HttpServletRequest request) {
        model.addAttribute("teacherInfo", famousTeacherStyleService.getByFaId(fastId));
        model.addAttribute("module", famousTeacherStyleService.getById(fastId));
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "teacherDetail", "名师风采");
        return "view/teacherDetail";
    }

}
