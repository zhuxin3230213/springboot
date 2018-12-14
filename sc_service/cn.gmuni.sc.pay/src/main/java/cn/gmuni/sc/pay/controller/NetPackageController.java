package cn.gmuni.sc.pay.controller;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.pay.model.NetPackage;
import cn.gmuni.sc.pay.service.INetPackageService;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@Api(value = "/netPackage",description = "网费套餐管理控制类")
@RestController
@RequestMapping(value ="/netPackage")
public class NetPackageController {

    @Autowired
    @Qualifier("netPackageServiceImpl")
    INetPackageService netPackageServiceImpl;

    @ApiOperation(value = "网费套餐查询")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolCode",value = "学校编码"),
            @ApiImplicitParam(name = "name",value = "套餐名称"),
            @ApiImplicitParam(name = "code", value = "套餐编码"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<NetPackage> list(@RequestBody @ApiIgnore Map<String,Object> params){
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return  new PageInfo<>(netPackageServiceImpl.getNetPackage(params));
    }

    @ApiOperation(value = "网费套餐添加")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Content list(@RequestBody NetPackage netPackage){
        return netPackageServiceImpl.addNetPackage(netPackage);
    }

    @ApiOperation(value = "网费套餐修改")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Content update(@RequestBody NetPackage netPackage){
        return netPackageServiceImpl.updateNetPackage(netPackage);
    }


    @ApiOperation(value = "网费套餐删除")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "所要删除的网费套餐id")
    })
    public Content del(@RequestBody @ApiIgnore Map<String,Object> params){
        return netPackageServiceImpl.delNetPackage( Arrays.asList(String.valueOf(params.get("ids")).split(",")));
    }


}
