package cn.gmuni.org.controller;

import cn.gmuni.org.model.Department;
import cn.gmuni.org.service.IDepartmentService;
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
 * 组织机构管理控制类
 */
@Api(value = "/dept", description = "组织机构管理控制类")
@RestController
@RequestMapping(value = "dept")
public class DepartmentController {

    @Autowired
    @Qualifier("departmentServiceImpl")
    IDepartmentService deptService;

    /**
     * 新增部门
     *
     * @param dept
     * @return
     */
    @ApiOperation(value = "新增部门")
    @RequestMapping(value = "addDept", method = RequestMethod.POST)
    @ResponseBody
    public Department addDept(@RequestBody Department dept) {
        return deptService.addDept(dept);
    }

    /**
     * 获取组织机构树
     *
     * @return
     */
    @ApiOperation(value = "获取组织机构树")
    @RequestMapping(value = "getAllDepts", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllDepts() {
        return deptService.getAllDepts();
    }

    /**
     * 修改部门
     *
     * @param dept
     * @return
     */
    @ApiOperation(value = "修改部门")
    @RequestMapping(value = "updateDept", method = RequestMethod.POST)
    @ResponseBody
    public Department updateDept(@RequestBody Department dept) {
        return deptService.updateDept(dept);
    }

    /**
     * 根据id获取子部门
     *
     * @return
     */
    @ApiOperation(value = "根据id获取子部门")
    @RequestMapping(value = "getDeptListById", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "节点部门组织id", required = true),
            @ApiImplicitParam(name = "code", value = "查询条件：部门编码"),
            @ApiImplicitParam(name = "name", value = "查询条件：部门名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @ResponseBody
    public PageInfo<Department> getDeptListById(@RequestBody @ApiIgnore Map<String, String> params) {
        return deptService.getDeptListById(params);
    }

    /**
     * 删除部门
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "删除部门", notes = "返回值包含状态码（flag），以及错误信息（msg）{hasChildren：包含子节点，hasUser：包含人员}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "要删除的节点部门组织id", required = true)})
    @RequestMapping(value = "delDept", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDept(@RequestBody @ApiIgnore Map<String, String> params) {
        return deptService.delDept(params.get("deptId"));
    }

    /**
     * 根据id获组织机构
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "根据id获组织机构")
    @RequestMapping(value = "getDeptById", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "节点部门组织id", required = true)})
    @ResponseBody
    public Department getDeptById(@RequestBody @ApiIgnore Map<String, String> params) {
        return deptService.getDeptById(params.get("deptId"));
    }

    /**
     * 校验重名
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "校验重名" , notes = "返回{flag:true or false,msg:(nameExist:名称已存在，codeExist：编码已存在)}")
    @RequestMapping(value = "checkNameExist", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "id", value = "id"),
            @ApiImplicitParam(name = "parentId", value = "上级id"),
            @ApiImplicitParam(name = "code", value = "编码")})
    @ResponseBody
    public Map<String, Object> checkNameExist(@RequestBody @ApiIgnore Map<String, String> params) {
        return deptService.checkNameExist(params);
    }

    /**
     * 启用禁用
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "启用禁用", notes = "0：禁用，1:启用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id", required = true),
            @ApiImplicitParam(name = "status", value = "0：禁用，1:启用", required = true)})
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestBody @ApiIgnore Map<String, String> params) {
        return deptService.updateStatus(params.get("deptId"), params.get("status"));
    }

    /**
     * 部门调动
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "部门调动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id", required = true),
            @ApiImplicitParam(name = "parentId", value = "目标部门id", required = true)})
    @RequestMapping(value = "transferDept", method = RequestMethod.POST)
    @ResponseBody
    public Department transferDept(@RequestBody @ApiIgnore Map<String, String> params) {
        return deptService.transferDept(params.get("deptId"), params.get("parentId"));
    }
    /**
     * 根据人员id查询兼职部门列表
     * @param params
     * @return
     */
    @ApiOperation(value = "根据人员id查询兼职部门列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "人员id", required = true)})
    @RequestMapping(value = "listPartDeptByUserId", method = RequestMethod.POST)
    @ResponseBody
    public List<Department> listPartDeptByUserId(@RequestBody @ApiIgnore Map<String, String> params){
        return deptService.listPartDeptByUserId(params);
    }

    /**
     * 保存人员兼职部门信息
     * @param params
     * @return
     */
    @ApiOperation(value = "保存人员兼职部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "人员id", required = true),
            @ApiImplicitParam(name = "deptIds", value = "部门id，逗号分割", required = true)})
    @RequestMapping(value = "savePartInfos", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> savePartInfos(@RequestBody @ApiIgnore Map<String, String> params){
        return deptService.savePartInfos(params);
    }
}
