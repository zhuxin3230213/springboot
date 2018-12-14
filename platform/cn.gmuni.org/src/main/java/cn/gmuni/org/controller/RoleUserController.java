package cn.gmuni.org.controller;

import cn.gmuni.org.model.Role;
import cn.gmuni.org.model.UserInfo;
import cn.gmuni.org.service.IRoleUserService;
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
 * 角色授权控制类
 */
@Api(value = "/roleuser", description = "角色授权控制类")
@RestController
@RequestMapping(value = "roleuser")
public class RoleUserController {

    @Qualifier("roleUserServiceImpl")
    @Autowired
    IRoleUserService roleUserService;

    /**
     * 根据角色id授权给该角色的用户
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "根据角色id授权给该角色的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true)})
    @RequestMapping(value = "listUserForAuthorize", method = RequestMethod.POST)
    @ResponseBody
    public List<UserInfo> listUserForAuthorize(@RequestBody @ApiIgnore Map<String, String> params) {
        return roleUserService.listUserForAuthorize(params);
    }

    /**
     * 根据用户id授权给该角色的用户
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "根据用户id授权给该角色的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)})
    @RequestMapping(value = "listRoleForAuthorize", method = RequestMethod.POST)
    @ResponseBody
    public List<Role> listRoleForAuthorize(@RequestBody @ApiIgnore Map<String, String> params) {
        return roleUserService.listRoleForAuthorize(params);
    }

    /**
     * 角色管理模块，保存选中的用户授权信息
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "角色管理模块，保存选中的用户授权信息", notes = "返回执行结果{flag:true or false}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true),
            @ApiImplicitParam(name = "userIds", value = "逗号分割的用户id", required = true)})
    @RequestMapping(value = "authorizeUserToRole", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> authorizeUserToRole(@RequestBody @ApiIgnore Map<String, String> params) {
        return roleUserService.authorizeUserToRole(params);
    }

    /**
     * 用户管理模块，保存选中的角色授权信息
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "用户管理模块，保存选中的角色授权信息", notes = "返回执行结果{flag:true or false}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleIds", value = "逗号分割的角色id", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)})
    @RequestMapping(value = "authorizeRoleToUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> authorizeRoleToUser(@RequestBody @ApiIgnore Map<String, String> params) {
        return roleUserService.authorizeRoleToUser(params);
    }


}
