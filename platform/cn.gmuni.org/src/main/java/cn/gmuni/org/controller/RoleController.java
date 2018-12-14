package cn.gmuni.org.controller;

import cn.gmuni.org.model.Role;
import cn.gmuni.org.service.IRoleService;
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
 * 用户角色控制类
 */
@Api(value = "/role", description = "用户角色控制类")
@RestController
@RequestMapping(value = "role")
public class RoleController {
    @Autowired
    @Qualifier("roleServiceImpl")
    IRoleService roleService;
    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "新增角色")
    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    @ResponseBody
    public Role addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    /**
     * 删除角色
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "返回值包含状态码（flag），，以及错误信息（msg）{hasBeenAuthorized：已被授权给用户}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "要删除的角色id数字字符串分割", required = true)})
    @RequestMapping(value = "delRole", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delRole(@RequestBody @ApiIgnore Map<String, String> params) {
        return roleService.delRole(params.get("roleId"));
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "修改角色")
    @RequestMapping(value = "updateRole", method = RequestMethod.POST)
    @ResponseBody
    public Role updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    /**
     * 查询角色列表
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "查询条件：角色编码"),
            @ApiImplicitParam(name = "name", value = "查询条件：角色名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @RequestMapping(value = "listRole", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<Role> listRole(@RequestBody @ApiIgnore Map<String, String> params) {
        return roleService.listRole(params);
    }
    /**
     * 查询所有角色列表无分页
     *
     * @return
     */
    @ApiOperation(value = "查询所有角色列表无分页")
    @RequestMapping(value = "listAllRole", method = RequestMethod.POST)
    @ResponseBody
    public List<Role> listAllRole() {
        return roleService.listAllRole();
    }
    /**
     * 校验名称编码
     * @param params
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "返回值包含状态码（flag）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "code", value = "编码", required = true)})
    @RequestMapping(value = "checkNameAndCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkNameAndCode(@RequestBody @ApiIgnore Map<String, String> params){
       return roleService.checkNameAndCode(params.get("name"), params.get("code"));
    }


}
