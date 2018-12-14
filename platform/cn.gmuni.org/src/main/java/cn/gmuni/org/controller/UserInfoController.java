package cn.gmuni.org.controller;

import cn.gmuni.org.model.User;
import cn.gmuni.org.model.UserInfo;
import cn.gmuni.org.service.IUserInfoService;
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
 * 用户管理控制类
 *
 * @author sunwn
 */
@Api(value = "/userinfo", description = "用户管理控制类")
@RestController
@RequestMapping(value = "userinfo")
public class UserInfoController {

    @Autowired
    @Qualifier("userInfoServiceImpl")
    IUserInfoService userInfoService;

    /**
     * 列出所有非用户人员
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "列出所有非用户人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "节点部门组织id", required = true),
            @ApiImplicitParam(name = "code", value = "查询条件：人员编码"),
            @ApiImplicitParam(name = "name", value = "查询条件：人员名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @RequestMapping(value = "listUndefinedUser", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<User> listUndefinedUser(@RequestBody @ApiIgnore Map<String, String> params) {
        return userInfoService.listUndefinedUser(params);
    }

    /**
     * 根据传入的人员id列表添加用户
     */
    @ApiOperation(value = "根据传入的人员id列表添加用户", notes = "返回值包含状态码（flag")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userInfo", value = "要添加的人员信息", required = true)})
    @RequestMapping(value = "addUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUserInfo(@RequestBody @ApiIgnore UserInfo userInfo) {
        return userInfoService.addUserInfo(userInfo);
    }

    /**
     * 根据传入的用户id列表删除用户
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "根据传入的用户id列表删除用户", notes = "返回值包含状态码（flag")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infoIds", value = "逗号分割的id", required = true)})
    @RequestMapping(value = "delUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delUserInfo(@RequestBody @ApiIgnore Map<String, String> params) {
        return userInfoService.delUserInfo(params.get("infoIds"));
    }

    /**
     * 列出所有用户
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "列出所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "查询条件：用户编码"),
            @ApiImplicitParam(name = "userName", value = "查询条件：用户名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @RequestMapping(value = "listAllUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<UserInfo> listAllUserInfo(@RequestBody @ApiIgnore Map<String, String> params) {
        return userInfoService.listAllUserInfo(params);
    }
    /**
     * 列出部门下属所有用户
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "列出部门下属所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id"),
            @ApiImplicitParam(name = "code", value = "查询条件：用户编码"),
            @ApiImplicitParam(name = "userName", value = "查询条件：用户名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @RequestMapping(value = "listAllUserInfoByDeptId", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<UserInfo> listAllUserInfoByDeptId(@RequestBody @ApiIgnore Map<String, String> params) {
        return userInfoService.listAllUserInfoByDeptId(params);
    }

    /**
     * 密码初始化
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "密码初始化", notes = "返回值包含状态码（flag")
    @RequestMapping(value = "initializePwd", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infoIds", value = "逗号分割的id", required = true)})
    @ResponseBody
    public Map<String, Object> initializePwd(@RequestBody @ApiIgnore Map<String, String> params) {
        return userInfoService.initializePwd(params.get("infoIds"));
    }

    /**
     * 启用：1，禁用：0用户
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "启用：1，禁用：0用户", notes = "返回值包含状态码（flag")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infoIds", value = "逗号分割的id", required = true),
            @ApiImplicitParam(name = "state", value = "启用：1，禁用：0", required = true)})
    @RequestMapping(value = "changeState", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeState(@RequestBody @ApiIgnore Map<String, String> params) {
        return userInfoService.changeState(params.get("infoIds"),params.get("state"));
    }


    @ApiOperation(value = "true:成功,false:失败", notes = "返回值包含状态（flag")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newPwd",value = "新密码",required = true),
            @ApiImplicitParam(name = "oldPwd",value = "旧密码",required = true),
            @ApiImplicitParam(name = "code",value = "用户编码",required = true)
    })
    @PostMapping(value = "changePwd")
    @ResponseBody
    public Map<String,Object> changePwd(@RequestBody @ApiIgnore Map<String,String> params){

        return userInfoService.changePwd(params.get("oldPwd"),params.get("newPwd"),params.get("code"));
    }
}
