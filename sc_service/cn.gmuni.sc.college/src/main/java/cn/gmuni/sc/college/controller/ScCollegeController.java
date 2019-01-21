package cn.gmuni.sc.college.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.college.cache.CollegeCache;
import cn.gmuni.sc.college.model.College;
import cn.gmuni.sc.college.service.ICollegeService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Api(value = "/bg/college",description ="学校管理控制类")
@RestController
@RequestMapping("/bg/college")
public class ScCollegeController {

    @Autowired
    @Qualifier("collegeService")
    ICollegeService collegeService;

    @GetMapping("/listAll")
    public BaseResponse<List<College>> listAll(){
        List<College> colleges = CollegeCache.list();
        List<College> result = new ArrayList<>();
        for(College c: colleges){
            College college = new College();
            college.setCode(c.getCode());
            college.setName(c.getName());
            result.add(college);
        }
        return new BaseResponse<>(result);
    }


    @ApiOperation(value = "新增学校信息")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
     public Content addSchool(@RequestBody College college){
        return collegeService.addSchool(college);
     }

    @ApiOperation(value = "编辑学校信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Content updateSchool(@RequestBody College college){
        return collegeService.updateSchool(college);
    }

    @ApiOperation(value = "删除学校信息")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "所要删除的学校的id", required = true)
    })
    public Content delSchool(@RequestBody @ApiIgnore Map<String,Object> params){
        List<String> ids = Arrays.asList(((String)params.get("ids")).split(","));
        return collegeService.deslSchool(ids);
    }

    @ApiOperation(value = "查询学校信息")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "所查询的学校名称"),
            @ApiImplicitParam(name = "code", value = "所查询的学校编码"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<College> listSchool(@RequestBody @ApiIgnore Map<String,Object> params){
        return new PageInfo<>(collegeService.listSchool(params));
    }

    @ApiOperation(value = "查询学校信息")
    @RequestMapping(value = "/getSchool", method = RequestMethod.POST)
    public List<Map<String,String>> getSchool(){
        return collegeService.getSchool();
    }
}
