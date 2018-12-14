package cn.gmuni.enrollment.guide.controller;

import cn.gmuni.enrollment.guide.service.IInfoContentService;
import cn.gmuni.maintenance.model.InfoContent;
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
 * 内容管理通用类
 */
@Api(value = "infoContent", description = "内容管理通用类")
@RestController
@RequestMapping("infoContent")
public class InfoContentController {
    @Autowired
    @Qualifier("infoContentServiceImpl")
    IInfoContentService infoContentService;


    /**
     * 根据内容所属模块的类别列表查询信息
     *
     * @return
     */
    @ApiOperation(value = "根据内容所属模块的类别列表查询信息")
    @RequestMapping(value = "listContents", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "moduleName", value = "模块的名称（编码）", required = true),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @ResponseBody
    public PageInfo<InfoContent> listContents(@RequestBody @ApiIgnore Map<String, String> params) {
        return infoContentService.listContents(params);
    }

    @ResponseBody
    @PostMapping("/listInfoByMId")
    @ApiOperation(value="根据内容所属模块查询文章信息，不使用分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "moduleName", value = "模块的名称（编码）", required = true)})
    public List<InfoContent> listInfoByModuleId(@RequestBody @ApiIgnore Map<String, Object> params){
        return infoContentService.listInfoByModuleId(params);
    }

    /**
     * 保存或者更新信息
     * @param info
     * @return
     */
    @ApiOperation(value = "保存或者更新信息", notes = "对象有Id为更新，id为空即为新增")
    @RequestMapping(value = "saveOrUpDateContent", method = RequestMethod.POST)
    @ResponseBody
    public InfoContent saveOrUpDateContent(@RequestBody InfoContent info) {
        return infoContentService.saveOrUpDateContent(info);
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
        return infoContentService.delInfoByIds(params);
    }

}
