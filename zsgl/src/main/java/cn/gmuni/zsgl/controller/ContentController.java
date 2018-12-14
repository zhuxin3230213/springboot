package cn.gmuni.zsgl.controller;

import cn.gmuni.zsgl.cache.FooterCache;
import cn.gmuni.zsgl.cache.HeaderCache;
import cn.gmuni.zsgl.dao.ContentRepository;
import cn.gmuni.zsgl.entity.UpdateClickThrough;
import cn.gmuni.zsgl.service.*;
import cn.gmuni.zsgl.service.impl.CacheRemove;
import cn.gmuni.zsgl.util.PostNewsRegulation;
import cn.gmuni.zsgl.util.Result;
import cn.gmuni.zsgl.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author:ZhuXin
 * @Description: 招生系统正文控制层
 * @Date:Create in 17:13 2018/5/7
 * @Modified By:
 **/
@Api(value = "正文接口类", description = "正文服务API根目录")
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    @Qualifier("contentServiceImpl")
    ContentService contentService;

    @Autowired
    @Qualifier("menuServiceImpl")
    MenuService menuService;

    @Autowired
    @Qualifier("facultyProfessionServiceImpl")
    FacultyProfessionService facultyProfessionService;

    @Autowired
    @Qualifier("postNewsRegulationServiceImpl")
    PostNewsRegulationService postNewsRegulationService;

    @Autowired
    @Qualifier("visitStatisticsServiceImpl")
    VisitStatisticsService visitStatisticsService;

    @Autowired
    @Qualifier("uploadServiceImpl")
    UploadService uploadService;

    @Autowired
    @Qualifier("slideShowServiceImpl")
    SlideShowService slideShowService;

    @Autowired
    @Qualifier("professionContentServiceImpl")
    ProfessionContentService professionContentService;

    @Autowired
    @Qualifier("configServiceImpl")
    ConfigService configService;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    @Qualifier("updateClickThroughServiceImpl")
    UpdateClickThroughService updateClickThroughService;

    @Autowired
    CacheRemove cacheRemove;

    @Value("${contentPage.currentPage}")
    private Integer currentPage;
    @Value("${contentPage.pageSize}")
    private Integer pageSize;
    @Value("${regulation.pageSize}")
    private Integer regPageSize;

    @Value("${postNewsRegulation.currentPage}")
    private Integer pnrCurrentPage;
    @Value("${postNewsRegulation.pageSize}")
    private Integer pnrPageSize;

    @Value("${host.hostName}")
    private String hostName;
    private  String hotName; //热点信息模块名称
    @Value("${host.pageSize}")
    private Integer hostPageSize;

    @Value("${contact.urls}")
    private String urlCode;

    @Value("${attachmentContentType}")
    private String attachmentContentType;

    @Value("${fap.status}")
    private String status;

    @Value("${globalSearch.currentPage}")
    private Integer searchCurrentPage;

    @Value("${globalSearch.pageSize}")
    private Integer searchPageSize;

    @Value("${hots.facMenuCode}")
    private String facMenuCode;

    @Value("${content.type}")
    private String contentType;
    @Value("${pnr.type}")
    private String pnrType;


    /**
     * 查询信息所有正文
     *
     * @return
     */
    @ApiOperation(value = "获取首页正文信息", notes = "")
    @GetMapping(value = "/welcome",produces = "text/plain;charset=UTF-8")
    public String findAll(Model model, HttpServletRequest request) {
        model.addAttribute("getPosts", postNewsRegulationService.getPageByTypeLimit
                (PostNewsRegulation.Post.getIndex(), currentPage, pageSize));
        model.addAttribute("getNews", postNewsRegulationService.getPageByTypeLimit
                (PostNewsRegulation.News.getIndex(), currentPage, pageSize));
        model.addAttribute("getRegulation", postNewsRegulationService.getPageByTypeLimit
                (PostNewsRegulation.Regulation.getIndex(), currentPage, pageSize));
        model.addAttribute("hots", contentService.getByClickHosts(facMenuCode, currentPage, pnrPageSize));
        model.addAttribute("listSlides", slideShowService.listSlideShows());
        model.addAttribute("contactUrls", configService.getConfigByCode(urlCode));
        visitStatisticsService.add(request, "welcome", "首页");
        model.addAttribute("header", HeaderCache.getHeaderHtml());
        //model.addAttribute("listMenus",menuService.menuTree("1"));
        model.addAttribute("footer", FooterCache.getFooterHtml());
        // model.addAttribute("configUtil",configService.listConfigsByCode());
        return "welcome";
    }


    /**
     * 根据关键字获取搜素列表
     *
     * @param keyWord
     * @param model
     * @param request
     * @return
     */
    @ApiOperation(value = "根据关键字获取搜素列表", notes = "")
    @GetMapping(value = "/search/{keyWord}")
    public String listSearchs(@PathVariable("keyWord") String keyWord, Model model, HttpServletRequest request) {
        model.addAttribute("listSearchs", contentService.listSearchs(keyWord, searchCurrentPage, searchPageSize));
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("pageTotal", contentService.getSearchsTotalPage());
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "search", "全局搜索");
        return "view/search";
    }


    /**
     * 全局搜索分页
     *
     * @param currentPage
     * @param request
     * @return
     */
    @ApiOperation(value = "全局搜索分页", notes = "")
    @GetMapping(value = "/searchPage/{keyWord}/{currentPage}")
    @ResponseBody
    public Result getSearchsPage(@PathVariable("keyWord") String keyWord, @PathVariable("currentPage") Integer currentPage, HttpServletRequest request) {
        visitStatisticsService.add(request, "search", "全局搜索");
        return ResultUtil.success(contentService.listSearchs(keyWord, currentPage, searchPageSize));

    }


    /**
     * 根据搜索id和type类型获取正文信息
     * type: 0：菜单 1：content（正文） 2：pnr（信息）
     * 3：faculty（学科专业） 4: famousTeacherStyle （名师风采）
     *
     * @param id
     * @param type
     * @param request
     * @return
     */
    @ApiOperation(value = "根据搜索id和type类型获取正文信息", notes = "")
    @GetMapping(value = "/searchContent/{id}/{type}")
    public String searchContent(@PathVariable("id") String id,
                                @PathVariable("type") String type,
                                HttpServletRequest request) {
        if ("0".equals(type)) {//菜单
            if ("2003".equals(id) || "2005".equals(id) || "2008".equals(id)
                    || "2009".equals(id)) {
                return "redirect:/content/content_detail_menu/" + id;
            } else if ("2001".equals(id) || "2002".equals(id)) {
                return "redirect:/history/plan/" + id;
            } else if ("2004".equals(id)) {
                return "redirect:/fap/facps";
            } else if ("2006".equals(id)) {
                return "redirect:/fast/tea/" + id;
            } else {
                return "redirect:/content/title_list/" + id;
            }
        } else if ("1".equals(type)) {//content(正文)
            String meunCode = contentService.getContentById(id).getParentCode();
            return "redirect:/content/content_details/" + id + "/" + meunCode;
        } else if ("2".equals(type)) {//pnr(正文)
            return "redirect:/content/content_detail_notice/" + id;
        } else if ("3".equals(type)) {//学科专业(deptId)
            return "redirect:/content/content_detail/" + id;
        } else if ("4".equals(type)) {//名师风采正文
            return "redirect:/fast/teacherDetail/" + id;
        } else {
            return null;
        }

    }

    /**
     * 轮播图进入相应模版
     * type类型：
     * 0 ：content（菜单内容）
     * 1：pnr（信息内容）
     *
     * @param articleId
     * @param module
     * @param type
     * @param request
     * @return
     */
    @ApiOperation(value = "获取轮播正文", notes = "")
    @RequestMapping(value = "/moduleContent/{articleId}/{module}/{type}")
    public String moduleContent(@PathVariable("articleId") String articleId,
                                @PathVariable("module") String module,
                                @PathVariable("type") String type, HttpServletRequest request) {
        if ("0".equals(type)) {
            if (module.equals("2003") || module.equals("2005") || module.equals("2008")
                    || module.equals("2009")) {
                return "redirect:/content/content_detail_menu/" + module;
            } else {
                return "redirect:/content/content_details/" + articleId + "/" + module;
            }
        } else if ("1".equals(type)) {
            return "redirect:/content/content_detail_notice/" + articleId;
        } else {
            return null;
        }
    }


    /**
     * 获取热点信息分页
     *
     * @param currentPage
     * @param request
     * @return
     */
    @ApiOperation(value = "获取热点信息分页", notes = "")
    @GetMapping(value = "/hotsPage")
    @ResponseBody
    public Result getHotsPage(@RequestParam("currentPage") Integer currentPage, HttpServletRequest request) {
        visitStatisticsService.add(request, "welcome", "热点信息");
        return ResultUtil.success(contentService.getByClickHosts(facMenuCode, currentPage, pnrPageSize));
    }


    /**
     * "更多"进入信息公告列表
     *
     * @param type
     * @param model
     * @return
     */
    @ApiOperation(value = "信息公告列表", notes = "")
    @GetMapping(value = "/title_list_notice/{type}")
    public String listsPNR(@PathVariable("type") String type, Model model, HttpServletRequest request) {
        model.addAttribute("noticeTitleLists", postNewsRegulationService.getByTypeNamePage
                (type, currentPage, hostPageSize));
        model.addAttribute("module", postNewsRegulationService.typeName(type));
        model.addAttribute("type", type);
        model.addAttribute("hots", contentService.getByClickHosts(facMenuCode, currentPage, hostPageSize));
        model.addAttribute("pageTotal", postNewsRegulationService.getPageByTypeTotalPages(type, currentPage, hostPageSize));
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "title_list_notice", postNewsRegulationService.typeName(type));
        return "templates/title_list_notice";
    }

    /**
     * 更多"进入信息公告列表
     * 根据type类型获取信息列表，分页显示
     *
     * @param type
     * @param currentPage
     * @param request
     * @return
     */
    @ApiOperation(value = "信息公告列表分页", notes = "")
    @GetMapping(value = "/pnrPage")
    @ResponseBody
    public Result getPNRPage(@RequestParam("type") String type, @RequestParam("currentPage") Integer currentPage,
                             HttpServletRequest request) {
        return ResultUtil.success(postNewsRegulationService.getByTypeNamePage(type, currentPage, hostPageSize));
    }


    /**
     * 根据信息的id查询pnr
     * 返回信息列表
     *
     * @param pnrId
     * @param model
     * @return
     */
    //todo: compile return content(thymeleaf)
    @ApiOperation(value = "根据信息的id查询pnr", notes = "")
    @GetMapping(value = "/content_detail_notice/{contentId}")
    public String pnrContent(@PathVariable("contentId") String pnrId, Model model, HttpServletRequest request) {
        if (!StringUtils.isEmpty(pnrId)) {
            UpdateClickThrough updateClickThrough = updateClickThroughService.clickAdd(pnrId, pnrType);
            model.addAttribute("clickThrough", updateClickThrough.getClickThrough());
        }
        model.addAttribute("moduleName", postNewsRegulationService.typeName(postNewsRegulationService.getById(pnrId).getType()));
        model.addAttribute("module", postNewsRegulationService.getById(pnrId));
        model.addAttribute("cover", uploadService.getCoverByPnrId(pnrId));
        model.addAttribute("listAttachments", uploadService.listAttachmentsByPnrId(pnrId));
        model.addAttribute("noticeTitleLists", postNewsRegulationService.getPageByTypeLimit
                (postNewsRegulationService.getById(pnrId).getType(), pnrCurrentPage, pageSize));
        model.addAttribute("hosts", contentService.getByClickHosts(facMenuCode, pnrCurrentPage, pnrPageSize));
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "content_detail_notice",
                postNewsRegulationService.typeName(postNewsRegulationService.getById(pnrId).getType()));
        return "templates/content_detail_notice";
    }


    /**
     * 通过菜单menuCode询所有正文
     *
     * @param menuCode
     * @param model
     * @return
     */
    @ApiOperation(value = "通过菜单id查询所有正文", notes = "")
    @GetMapping(value = "/title_list/{subModuleId}")
    public String getContentByMenuId(@PathVariable("subModuleId") String menuCode, Model model, HttpServletRequest request) {
        model.addAttribute("titleLists", contentService.getPageByMenuCodeCache(menuCode, currentPage, hostPageSize));
        model.addAttribute("menu", menuService.getMenuByCode(menuCode));
        model.addAttribute("hosts", contentService.getByClickHosts(facMenuCode, pnrCurrentPage, hostPageSize));
        model.addAttribute("pageTotal", contentService.getPageByMenuCodeTotalPages(menuCode, currentPage, hostPageSize));
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "title_list",
                menuService.getMenuByCode(menuCode).getName());
        return "templates/title_list";
    }


    /**
     * 菜单查询所有正文，分页异步刷新
     *
     * @param currentPage
     * @param request
     * @return
     */
    @ApiOperation(value = "菜单查询所有正文分页", notes = "")
    @GetMapping(value = "/contentPage")
    @ResponseBody
    public Result getContentPage(@RequestParam("subModuleId") String menuCode,
                                 @RequestParam("currentPage") Integer currentPage, HttpServletRequest request) {
        return ResultUtil.success(contentService.getPageByMenuCodeCache(menuCode, currentPage, hostPageSize));
    }


    /**
     * 通过菜单的code直接查询正文
     *
     * @param menuCode
     * @param model
     * @return
     */
    //todo: compile return content(thymeleaf)
    @ApiOperation(value = "菜单直接查询正文", notes = "")
    @GetMapping(value = "/content_detail_menu/{subModuleId}")
    public String getContentByMenuCode(@PathVariable("subModuleId") String menuCode, Model model, HttpServletRequest request) {

        if (contentService.getByMenuCode(menuCode) != null) {
            String contentId = contentService.getContentByMenuCode(menuCode).getId();
            UpdateClickThrough updateClickThrough = updateClickThroughService.clickAdd(contentId, contentType);
            model.addAttribute("clickThrough", updateClickThrough.getClickThrough());
            model.addAttribute("content", contentService.getContentByMenuCode(menuCode));
            model.addAttribute("cover", uploadService.getCoverByContentId(contentId));
            model.addAttribute("listAttachments", uploadService.listAttachmentsByContentId(contentId));
            model.addAttribute("menu", menuService.getMenuByCode(menuCode));
            model.addAttribute("hots", contentService.getByClickHosts(facMenuCode, pnrCurrentPage, hostPageSize));
            model.addAttribute("header",HeaderCache.getHeaderHtml());
            model.addAttribute("footer",FooterCache.getFooterHtml());
            visitStatisticsService.add(request, "content_detail_menu",
                    menuService.getMenuByCode(menuCode).getName());
        } else {
            model.addAttribute("content", contentService.getContentById(null));
            model.addAttribute("cover", uploadService.getCoverByContentId(null));
            model.addAttribute("listAttachments", uploadService.listAttachmentsByContentId(null));
            model.addAttribute("menu", menuService.getMenuByCode(menuCode));
            model.addAttribute("hots", contentService.getByClickHosts(facMenuCode, pnrCurrentPage, hostPageSize));
            model.addAttribute("header",HeaderCache.getHeaderHtml());
            model.addAttribute("footer",FooterCache.getFooterHtml());
            visitStatisticsService.add(request, "content_detail_menu",
                    menuService.getMenuByCode(menuCode).getName());
        }

        return "templates/content_detail_menu";
    }


    /**
     * 通过学科专业的id查询对应的内容
     *
     * @param deptId
     * @param model
     * @return
     */
    //todo: compile return content(thymeleaf)
    @ApiOperation(value = "通过学科专业的id查询对应的内容", notes = "")
    @GetMapping(value = "/content_detail/{majorId}")
    public String getContentByMajorId(@PathVariable("majorId") String deptId, Model model, HttpServletRequest request) {

        if (!StringUtils.isEmpty(deptId)) {
            String contentId = professionContentService.getContentIdByDeptId(deptId);
            model.addAttribute("content", contentService.getConteByIdCache(contentId));
            model.addAttribute("listDepts", facultyProfessionService.listFacp());
        } else {
            model.addAttribute("content", contentService.getConteByIdCache(null));
            model.addAttribute("listDepts", facultyProfessionService.listFacp());
        }
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "majorDetail",
                "学科专业");
        return "view/majorDetail";
    }


    /**
     * 通过列表正文的id获取正文详细信息
     *
     * @param contentId
     * @param menuCode
     * @param model
     * @return
     */
    //todo: compile return content(thymeleaf)
    @ApiOperation(value = "通过正文的id获取正文详细信息", notes = "")
    @GetMapping(value = "/content_details/{contentId}/{menuId}")
    public String getContentById(@PathVariable("contentId") String contentId, @PathVariable("menuId") String menuCode, Model model,
                                 HttpServletRequest request) {
        if (!StringUtils.isEmpty(contentId)) {
            UpdateClickThrough updateClickThrough = updateClickThroughService.clickAdd(contentId, contentType);
            model.addAttribute("clickThrough", updateClickThrough.getClickThrough());
        }
        model.addAttribute("content", contentService.getConteByIdCache(contentId));
        model.addAttribute("cover", uploadService.getCoverByContentId(contentId));
        model.addAttribute("listAttachments", uploadService.listAttachmentsByContentId(contentId));
        model.addAttribute("titleLists", contentService.getPageByMenuCodeCache(menuCode, pnrCurrentPage, pnrPageSize));
        model.addAttribute("menu", menuService.getMenuByCode(menuCode));
        model.addAttribute("hots", contentService.getByClickHosts(facMenuCode, pnrCurrentPage, pnrPageSize));
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        return "templates/content_detail";


    }


    /**
     * 热点信息
     * 根据type类型区分热点信息
     * id查询对应的内容
     *
     * @param hostId
     * @param hostType
     * @param model
     * @return
     */
    //todo:compile return content(thymeleaf)
    @ApiOperation(value = "根据type类型区分热点信息", notes = "")
    @GetMapping(value = "/content_detail_hots/{contentId}/{hotsType}")
    public String getHost(@PathVariable("contentId") String hostId, @PathVariable(value = "hotsType") String hostType, Model model,
                          HttpServletRequest request) {
        model.addAttribute("hots", contentService.getByClickHosts(facMenuCode, currentPage, hostPageSize));
        try {
            hotName= new String(hostName.getBytes("ISO-8859-1"),"UTF-8"); //编码格式处理
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("moduleName", hotName);
        model.addAttribute("module", contentService.getByType(hostId, hostType));
        model.addAttribute("clickThrough", updateClickThroughService.clickThrough(hostId,hostType).getClickThrough());
        model.addAttribute("cover", uploadService.getCoverByHostId(hostId, hostType));
        model.addAttribute("listAttachments", uploadService.listAttachmentsByHostId(hostId, hostType));
        model.addAttribute("header",HeaderCache.getHeaderHtml());
        model.addAttribute("footer",FooterCache.getFooterHtml());
        visitStatisticsService.add(request, "content_detail_hots",
                hotName);
        return "templates/content_detail_hots";
    }


}
