package cn.gmuni.sysmenu.controller;

import cn.gmuni.sysmenu.model.Resource;
import cn.gmuni.sysmenu.service.IResourceService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 系统菜单管理控制类
 */
@Api(value = "/sysmenu", description = "系统菜单管理控制类")
@RestController
@RequestMapping(value = "sysmenu")
public class SysMenuController {
    @Qualifier("resourceServiceImpl")
    @Autowired
    IResourceService resourceService;

    /**
     * 新增菜单
     *
     * @param resource
     * @return
     */
    @ApiOperation(value = "新增菜单")
    @RequestMapping(value = "addResource", method = RequestMethod.POST)
    @ResponseBody
    public Resource addResource(@RequestBody Resource resource) {
        return resourceService.addResource(resource);
    }

    /**
     * 获取菜单树,不含按钮
     *
     * @return
     */
    @ApiOperation(value = "获取菜单树,不含按钮")
    @RequestMapping(value = "getAllResource", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllResource() {
        return resourceService.getAllResource();
    }
    /**
     * 获取菜单树,含按钮
     *
     * @return
     */
    @ApiOperation(value = "获取菜单树,含按钮")
    @RequestMapping(value = "getAllResourceForPriv", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllResourceForPriv() {
        return resourceService.getAllResourceForPriv();
    }


    /**
     * 修改菜单
     *
     * @param resource
     * @return
     */
    @ApiOperation(value = "修改菜单")
    @RequestMapping(value = "updateResource", method = RequestMethod.POST)
    @ResponseBody
    public Resource updateResource(@RequestBody Resource resource) {
        return resourceService.updateResource(resource);
    }

    /**
     * 根据id获下级菜单
     *
     * @return
     */
    @ApiOperation(value = "根据id获下级菜单")
    @RequestMapping(value = "getResourceListById", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceId", value = "节点id", required = true),
            @ApiImplicitParam(name = "code", value = "查询条件：菜单编码"),
            @ApiImplicitParam(name = "name", value = "查询条件：菜单名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @ResponseBody
    public PageInfo<Resource> getResourceListById(@RequestBody @ApiIgnore Map<String, String> params) {
        return resourceService.getResourceListById(params);
    }

    /**
     * 删除菜单
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "删除菜单", notes = "返回值包含状态码（flag），以及错误信息（msg）{hasChildren：包含子节点}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceId", value = "要删除菜单id，逗号分割", required = true)})
    @RequestMapping(value = "delResource", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delResource(@RequestBody @ApiIgnore Map<String, String> params) {
        return resourceService.delResource(params);
    }

    /**
     * 根据id获菜单
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "根据id获菜单")
    @RequestMapping(value = "getResourceById", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceId", value = "菜单id", required = true)})
    @ResponseBody
    public Resource getResourceById(@RequestBody @ApiIgnore Map<String, String> params) {
        return resourceService.getResourceById(params);
    }

    /**
     * 校验重名
     *
     * @param resource
     * @return
     */
    @ApiOperation(value = "校验重名", notes = "返回{flag:true or false,msg:(buttonCodeExist:按钮编码已存在， nameExist:名称已存在，codeExist：编码已存在,urlExist:url已存在)}")
    @RequestMapping(value = "checkNameExist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkNameExist(@RequestBody Resource resource) {
        return resourceService.checkNameExist(resource);
    }

    /**
     * 启用禁用
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "启用禁用", notes = "0：禁用，1:启用,返回{flag:true or false")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceIds", value = "部门id,逗号分割", required = true),
            @ApiImplicitParam(name = "status", value = "0：禁用，1:启用", required = true)})
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestBody @ApiIgnore Map<String, String> params) {
        return resourceService.updateStatus(params);
    }
}
