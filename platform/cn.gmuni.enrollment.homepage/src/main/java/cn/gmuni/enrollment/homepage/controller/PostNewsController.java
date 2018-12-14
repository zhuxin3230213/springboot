package cn.gmuni.enrollment.homepage.controller;

import cn.gmuni.enrollment.homepage.model.PostNews;
import cn.gmuni.enrollment.homepage.service.IPostNewsService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 内容管理通用类
 */
@Api(value = "postNews", description = "内容管理通用类")
@RestController
@RequestMapping("postNews")
public class PostNewsController {
    @Autowired
    @Qualifier("postNewsServiceImpl")
    IPostNewsService postNewsService;


    /**
     * 根据内容所属模块的类别列表查询信息
     *
     * @return
     */
    @ApiOperation(value = "根据内容所属模块的类别列表查询信息")
    @RequestMapping(value = "listContents", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "模块的类型", required = true),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @ResponseBody
    public PageInfo<PostNews> listContents(@RequestBody @ApiIgnore Map<String, String> params) {
        return postNewsService.listContents(params);
    }

    /**
     * 保存或者更新信息
     * @param info
     * @return
     */
    @ApiOperation(value = "保存或者更新信息", notes = "对象有Id为更新，id为空即为新增")
    @RequestMapping(value = "saveOrUpDateContent", method = RequestMethod.POST)
    @ResponseBody
    public PostNews saveOrUpDateContent(@RequestBody PostNews info) {
        return postNewsService.saveOrUpDateContent(info);
    }

    /**
     * 根据id删除信息
     * @param params
     * @return
     */
    @ApiOperation(value = "根据id删除信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)})
    @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteByIds(@RequestBody @ApiIgnore Map<String, String> params){
        return postNewsService.delInfoByIds(params);
    }

}
