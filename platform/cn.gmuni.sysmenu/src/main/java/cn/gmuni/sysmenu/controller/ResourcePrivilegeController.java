package cn.gmuni.sysmenu.controller;

import cn.gmuni.sysmenu.model.ResourcePrivilege;
import cn.gmuni.sysmenu.service.IResourcePrivilegeService;
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
 * 资源授权控制类
 *
 * @author sunwn
 */
@Api(value = "/resourcePrivilege", description = "资源授权控制类")
@RestController
@RequestMapping(value = "resourcePrivilege")
public class ResourcePrivilegeController {


    @Autowired
    @Qualifier("resourcePrivilegeServiceImpl")
    IResourcePrivilegeService service;

    /**
     * 保存授权信息
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "保存授权信息", notes = "返回值包含状态码（flag）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "privType", value = "权限类型1:组织机构 2：角色 3：工作组", required = true),
            @ApiImplicitParam(name = "privCode", value = "部门，角色id", required = true),
            @ApiImplicitParam(name = "resourceCodes", value = "资源id，逗号分割的数组", required = true)
    })
    @RequestMapping(value = "savePrivilege", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> savePrivilege(@RequestBody @ApiIgnore Map<String, String> params) {
        return service.savePrivilege(params);
    }

    /**
     * 根据部门或者角色id查找权限信息
     *
     * @param params
     * @return
     */
    @ApiOperation(value = " 根据部门或者角色id查找权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "privCode", value = "部门，角色id", required = true)
    })
    @RequestMapping(value = "getPrivByObjId", method = RequestMethod.POST)
    @ResponseBody
    public List<ResourcePrivilege> getPrivByObjId(@RequestBody @ApiIgnore Map<String, String> params) {
        return service.getPrivByObjId(params);
    }
}
