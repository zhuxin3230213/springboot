package com.gmunidata.classinfo.controller;

import com.github.pagehelper.PageInfo;
import com.gmunidata.base.response.Content;
import com.gmunidata.classinfo.model.ClassInfo;
import com.gmunidata.classinfo.service.IClassInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 班级管理控制类
 */
@Api(value = "/class",description ="班级管理控制类" )
@RestController
@RequestMapping(value = "class" )
public class ClassInfoController  {

    @Autowired
    @Qualifier("classInfoServiceImp")
    IClassInfoService classInfoServiceImp;


    @ApiOperation(value = "新增班级", notes = "返回值包含状态码（flag)以及信息（message）返回内容（content）")
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Content save(@RequestBody ClassInfo info){
        return classInfoServiceImp.saveClass(info);
}

    @ApiOperation(value = "编辑班级", notes = "返回值包含状态码（flag)以及信息（message）返回内容（content）")
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public Content edit(@RequestBody ClassInfo info){
        return classInfoServiceImp.editClass(info);
    }


    /**
     * {
     *     flag:true,false
     * }
     */
    @ApiOperation(value = "删除班级", notes = "返回值包含状态码（flag），以及错误信息（msg）{hasStudent：包含学生}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "要删除的班级的id", required = true)})
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delete(@RequestBody  @ApiIgnore Map<String,String> params){
        return classInfoServiceImp.deleteClass(params.get("classId"));
    }


    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询班级 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年级"),
            @ApiImplicitParam(name = "subjectName", value = "所属专业的名称"),
            @ApiImplicitParam(name = "subjectId", value = "所属专业的id"),
            @ApiImplicitParam(name = "name", value = "查询条件 名称"),
            @ApiImplicitParam(name = "code", value = "查询条件 编码"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<Map<String,Object>> list(@RequestBody @ApiIgnore Map<String, String> params){
        return  classInfoServiceImp.listClass(params);
    }


    @RequestMapping(value = "getClass",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据专业id查询所有专业的班级 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectId", value = "所属专业的id")})
    public List<Map<String,Object>> getClass(@RequestBody @ApiIgnore Map<String, String> params){
        return  classInfoServiceImp.getClass(params);
    }

    @RequestMapping(value = "outClass",method = RequestMethod.POST)
    @ApiOperation(value = "导出班级表 ")
    public  void  outClass(HttpServletResponse response){
        classInfoServiceImp.outClass(response);
    }

    @RequestMapping(value = "modelClass",method = RequestMethod.POST)
    @ApiOperation(value = "导出班级表模板 ")
    public  void  modelClass(HttpServletResponse response){
        classInfoServiceImp.modelClass(response);
    }

    @RequestMapping(value = "inClass",method = RequestMethod.POST)
    @ApiOperation(value = "导入班级表 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "uuid",required = true),
            @ApiImplicitParam(name = "merge",value = "是否覆盖重复数据（0:否 1：是）",required = true),
            @ApiImplicitParam(name = "name",value = "sheet页名称",required = true),
            @ApiImplicitParam(name = "addOrCover",value = "追加或者覆盖",required = true),
            @ApiImplicitParam(name = "startLine",value = "起始行",required = true),
            @ApiImplicitParam(name = "startClu",value = "起始列",required = true)
    })
    public  Content  inClass(@RequestBody @ApiIgnore Map<String,Object> params){
        return classInfoServiceImp.inClass(params);

    }


}
