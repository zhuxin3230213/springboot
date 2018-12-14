package cn.gmuni.enrollment.resource.controller;

import cn.gmuni.enrollment.resource.model.Catalog;
import cn.gmuni.enrollment.resource.service.ICatalogService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="/enrollment/catalog" , description = "招生系统目录管理")
@RestController
@RequestMapping("/enrollment/catalog")
public class CatalogController {

    @Autowired
    @Qualifier("catalogServiceImpl")
    ICatalogService catalogService;

    @RequestMapping(value = "/listAllCatalog" , method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="获取目录树")
    public List<Catalog> listAllCatalog(){
        List<Catalog> result = catalogService.listAllCatalog();
        return result;
    }


    @RequestMapping(value="/listAllCatalogByPid",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="通过父节点获取子目录，用于表格查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="code",value = "目录编码"),
            @ApiImplicitParam(name="parentId",value="父目录id"),
            @ApiImplicitParam(name = "name" , value = "目录名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)
    })
    public PageInfo<Catalog> listAllCatalogByPid(@RequestBody @ApiIgnore Map<String,Object> params){
        return catalogService.listAllCatalogByPid(params);
    }


    @PostMapping("/saveCatalog")
    @ResponseBody
    @ApiOperation(value="保存目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="catalog",value = "要保存的对象")
    })
    public Catalog saveCatalog(@RequestBody @ApiIgnore Catalog catalog){
        return catalogService.saveCatalog(catalog);
    }

    @PostMapping("/editCatalog")
    @ResponseBody
    @ApiOperation(value="修改目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="catalog",value = "要修改的对象")
    })
    public Catalog editCatalog(@RequestBody @ApiIgnore Catalog catalog){
        return catalogService.editCatalog(catalog);
    }

    @PostMapping("/checkNameCode")
    @ResponseBody
    @ApiOperation(value="校验编码与名称是否重复")
    @ApiImplicitParams({
            @ApiImplicitParam(name="catalog",value = "要校验的对象")
    })
    public Map<String,Object> checkNameCode(@RequestBody @ApiIgnore Catalog catalog){
        //先校验名称
        boolean checked = catalogService.checkName(catalog);
        Map<String,Object> result = new HashMap<>();
        if(!checked){
            checked = catalogService.checkCode(catalog);
            if(checked){
                result.put("flag",true);
                result.put("msg","hasCode");
            }else{
                result.put("flag",false);
            }
        }else{
            result.put("flag",true);
            result.put("msg","hasName");
        }
        //再校验编码
        return result;
    }

    @PostMapping("/removeCatalog")
    @ResponseBody
    public Map<String,Object> removeCatalog(@RequestBody Map<String,String> params){
        String[] ids = params.get("ids").split(",");
        Map<String,Object> result = catalogService.removeCatalog(ids);
        return result;
    }
}
