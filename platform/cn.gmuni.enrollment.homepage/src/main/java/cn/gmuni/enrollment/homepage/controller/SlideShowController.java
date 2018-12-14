package cn.gmuni.enrollment.homepage.controller;

import cn.gmuni.enrollment.homepage.service.ISlideShowService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 轮播图管理控制类
 */
@Api(value = "slideShow", description = "轮播图管理控制类")
@RestController
@RequestMapping("slideShow")
public class SlideShowController {

    @Autowired
    @Qualifier("slideShowServiceImpl")
    ISlideShowService slideShowService;

    /**
     * 查询系统已有轮播图
     *
     * @return
     */
    @ApiOperation(value = "查询系统已有轮播图", notes = "[{id:'轮播图表ID',articleId:'文章表ID',module:'所属模块'," +
            "title:'文章标题',createTime:'文章表新增时间',cover:'缩略图路径',sort:'缩略图排序号'}]")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> list() {
        return slideShowService.list();
    }

    /**
     * 按照指定模块查询文章
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "按照指定模块查询文章")
    @RequestMapping(value = "listArticle", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true),
            @ApiImplicitParam(name = "module", value = "所属模块", required = true),
            @ApiImplicitParam(name = "selectedId", value = "selectedId", required = true)})
    @ResponseBody
    public PageInfo<Map<String, Object>> listArticle(@RequestBody @ApiIgnore Map<String, String> params) {
        return slideShowService.listArticle(params);
    }

    /**
     * 保存轮播图信息
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "保存轮播图信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章表ID", required = true),
            @ApiImplicitParam(name = "module", value = "所属模块", required = true),
            @ApiImplicitParam(name = "sort", value = "缩略图排序号", required = true)})
    @ResponseBody
    public Map<String, Object> save(@RequestBody @ApiIgnore Map<String, String> params) {
        return slideShowService.save(params);
    }

    /**
     * 修改轮播图信息
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "修改轮播图信息")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轮播图id", required = true),
            @ApiImplicitParam(name = "articleId", value = "文章表ID", required = true),
            @ApiImplicitParam(name = "module", value = "所属模块", required = true),
            @ApiImplicitParam(name = "sort", value = "缩略图排序号", required = true)})
    @ResponseBody
    public Map<String, Object> update(@RequestBody @ApiIgnore Map<String, String> params) {
        return slideShowService.update(params);
    }

    /**
     * 删除轮播图信息
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "删除轮播图信息")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轮播图id", required = true)})
    @ResponseBody
    public Map<String, Object> delete(@RequestBody @ApiIgnore Map<String, String> params) {
        return slideShowService.delete(params);
    }

    /**
     * 调整轮播图顺序
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "调整轮播图顺序")
    @RequestMapping(value = "move", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轮播图id", required = true),
            @ApiImplicitParam(name = "sort", value = "缩略图排序号", required = true)})
    @ResponseBody
    public Map<String, Object> move(@RequestBody @ApiIgnore Map<String, String> params) {
        return slideShowService.move(params);
    }

}
