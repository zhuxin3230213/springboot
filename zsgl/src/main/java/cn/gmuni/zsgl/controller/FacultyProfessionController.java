package cn.gmuni.zsgl.controller;

import cn.gmuni.zsgl.cache.FooterCache;
import cn.gmuni.zsgl.cache.HeaderCache;
import cn.gmuni.zsgl.service.FacultyProfessionService;
import cn.gmuni.zsgl.service.VisitStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:ZhuXin
 * @Description: 院系专业控制层
 * @Date:Create in 11:50 2018/5/28
 * @Modified By:
 **/
@Api(value = "学科专业接口类", description = "学科专业服务API根目录")
@Controller
@RequestMapping("/fap")
public class FacultyProfessionController {


    @Autowired
    @Qualifier("facultyProfessionServiceImpl")
    FacultyProfessionService facultyProfessionService;

    @Autowired
    @Qualifier("visitStatisticsServiceImpl")
    VisitStatisticsService visitStatisticsService;

    @Value("${fap.status}")
    private String status;


    /**
     * 获取学科专业的树形结构
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "学科专业的树形结构", notes = "")
    @GetMapping(value = "/facps")
    public String findByFacp(Model model, HttpServletRequest request) {
        model.addAttribute("listDeptAndMajor", facultyProfessionService.listFacp());
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "majorInfo",
                "学科专业");
        return "view/majorInfo";
    }


}
