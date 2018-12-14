package cn.gmuni.org.controller;

import cn.gmuni.org.model.User;
import cn.gmuni.org.service.IUserService;
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
 * 人员管理控制类
 */
@Api(value = "/user", description = "人员管理控制类")
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    IUserService userService;

    /**
     * 新增人员
     * @param user
     * @return
     */
    @ApiOperation(value = "新增人员")
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * 修改人员
     * @param user
     * @return
     */
    @ApiOperation(value = "修改人员")
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    @ResponseBody
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 根据组织机构id查询人员列表
     * @param params
     * @return
     */
    @ApiOperation(value = "根据组织机构id查询人员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "节点部门组织id", required = true),
            @ApiImplicitParam(name = "code", value = "查询条件：人员编码"),
            @ApiImplicitParam(name = "name", value = "查询条件：人员名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @RequestMapping(value = "listUserByDeptId", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<User> listUserByDeptId(@RequestBody @ApiIgnore Map<String, String> params){
        return userService.listUserByDeptId(params);
    }

    /**
     * 根据组织机构id查询人员列表，排除已有兼职人员
     * @param params
     * @return
     */
    @ApiOperation(value = "根据组织机构id查询人员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "节点部门组织id", required = true),
            @ApiImplicitParam(name = "code", value = "查询条件：人员编码"),
            @ApiImplicitParam(name = "name", value = "查询条件：人员名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @RequestMapping(value = "listUserByDeptIdForPart", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<User> listUserByDeptIdForPart(@RequestBody @ApiIgnore Map<String, String> params){
        return userService.listUserByDeptIdForPart(params);
    }

    /**
     * 根据组织机构id查询兼职人员列表
     * @param params
     * @return
     */
    @ApiOperation(value = "根据组织机构id查询兼职人员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "节点部门组织id", required = true)})
    @RequestMapping(value = "listPartUserByDeptId", method = RequestMethod.POST)
    @ResponseBody
    public List<User> listPartUserByDeptId(@RequestBody @ApiIgnore Map<String, String> params){
        return userService.listPartUserByDeptId(params);
    }

    /**
     * 根据id删除人员<br/>
     * 逗号分割的id
     * @param params
     * @return
     */
    @ApiOperation(value = "根据id删除人员<br/>逗号分割的id", notes = "返回值包含状态码（flag")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uIds", value = "逗号分割的id", required = true)})
    @RequestMapping(value = "delUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delUser(@RequestBody @ApiIgnore Map<String, String> params){
      return userService.delUser(params.get("uIds"));
    }

    /**
     * 人员部门调动
     * @param params
     * @return
     */
    @ApiOperation(value = "人员部门调动", notes = "返回值包含状态码（flag")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uId", value = "逗号分割的id", required = true),
            @ApiImplicitParam(name = "deptId", value = "目标部门id", required = true)})
    @RequestMapping(value = "transferDept", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> transferDept(@RequestBody @ApiIgnore Map<String, String> params){
        return userService.transferDept(params.get("uId"), params.get("deptId"));
    }

    /**
     * 校验人员编码
     * @param params
     * @return
     */
    @ApiOperation(value = " 校验人员编码", notes = "返回值包含状态码（flag")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "人员编码", required = true)})
    @RequestMapping(value = "checkUserCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkUserCode(@RequestBody @ApiIgnore Map<String, String> params){
        return userService.checkUserCode(params.get("code"));
    }
}
