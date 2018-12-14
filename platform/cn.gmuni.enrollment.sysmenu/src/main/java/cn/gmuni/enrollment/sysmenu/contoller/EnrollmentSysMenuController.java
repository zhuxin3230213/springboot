package cn.gmuni.enrollment.sysmenu.contoller;



import cn.gmuni.enrollment.sysmenu.model.SysMenu;
import cn.gmuni.enrollment.sysmenu.service.ISysMenuService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="/enrollment/sysmenu",description = "招生系统系统菜单")
@RestController
@RequestMapping(value = "/enrollment/sysmenu")
public class EnrollmentSysMenuController {

    @Autowired
    @Qualifier("sysMenuServiceImpl")
    ISysMenuService sysMenuService;

    @PostMapping("/listAllSysMenu")
    @ResponseBody
    @ApiOperation(value="获取目录树",notes = "返回系统菜单树")
    public List<SysMenu> listAllSysMenu(){
        List<SysMenu> menus = sysMenuService.listAllSysMenu();
        List<SysMenu> result = new ArrayList<>();
        SysMenu root = new SysMenu();
        root.setId("-1");
        root.setBuiltIn("0");
        root.setCode("root");
        root.setLevel(0);
        root.setName("系统菜单");
        root.setSort(0);
        root.setStatus("1");
        root.setType("0");
        root.setChildren(menus);
        result.add(root);
        return result;
    }

    @RequestMapping(value="/listAllSysMenuByPid",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="通过父节点获取子目录，用于表格查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="code",value = "目录编码"),
            @ApiImplicitParam(name="parentId",value="父目录id"),
            @ApiImplicitParam(name = "name" , value = "目录名称"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)
    })
    public PageInfo<SysMenu> listAllSysMenuByPid(@RequestBody @ApiIgnore Map<String,Object> params){
        return sysMenuService.listAllSysMenuByPid(params);
    }


    @PostMapping("/saveSysMenu")
    @ResponseBody
    @ApiOperation(value="保存菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysmenu",value = "要保存的对象")
    })
    public SysMenu saveSysMenu(@RequestBody @ApiIgnore SysMenu sysmenu){
        return sysMenuService.saveSysMenu(sysmenu);
    }

    @PostMapping("/editSysMenu")
    @ResponseBody
    @ApiOperation(value="修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysmenu",value = "要修改的对象")
    })
    public SysMenu editSysMenu(@RequestBody @ApiIgnore SysMenu sysmenu){
        return sysMenuService.editSysMenu(sysmenu);
    }

    @PostMapping("/checkNameCode")
    @ResponseBody
    @ApiOperation(value="校验编码与名称是否重复")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysmenu",value = "要校验的对象")
    })
    public Map<String,Object> checkNameCode(@RequestBody @ApiIgnore SysMenu sysmenu){
        //先校验名称
        boolean checked = sysMenuService.checkName(sysmenu);
        Map<String,Object> result = new HashMap<>();
        if(!checked){
            checked = sysMenuService.checkCode(sysmenu);
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

    @ApiOperation(value = "删除系统菜单",notes = "返回对象，flag:true,flag:false,msg:hasChildren存在子节点,removeError:删除异常")
    @ApiImplicitParams({
            @ApiImplicitParam(name="ids",value = "要删除的菜单ID,用字符串隔开")
    })

    @PostMapping("/removeSysMenu")
    @ResponseBody
    public Map<String,Object> removeSysMenu(@RequestBody @ApiIgnore Map<String,String> params){
        String[] ids = params.get("ids").split(",");
        Map<String,Object> result = sysMenuService.removeSysMenu(ids);
        return result;
    }


}
