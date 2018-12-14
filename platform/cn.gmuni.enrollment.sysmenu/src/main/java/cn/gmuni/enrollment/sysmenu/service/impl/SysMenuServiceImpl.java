package cn.gmuni.enrollment.sysmenu.service.impl;

import cn.gmuni.enrollment.sysmenu.mapper.SysMenuMapper;
import cn.gmuni.enrollment.sysmenu.model.SysMenu;
import cn.gmuni.enrollment.sysmenu.service.ISysMenuService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.MyBatisPageUtils;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    SysMenuMapper mapper;

    @Override
    public List<SysMenu> listAllSysMenu() {
        List<SysMenu> menus = mapper.listAllSysMenu();
        return buildTree(menus,"-1");
    }

    @Override
    public PageInfo<SysMenu> listAllSysMenuByPid(Map<String, Object> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("name"));
        queryPara.put("parentId", params.get("parentId"));
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(mapper.listAllSysMenuByPid(queryPara));
    }

    @Override
    public SysMenu saveSysMenu(SysMenu sysmenu) {
        sysmenu.setId(IdGenerator.getId());
        sysmenu.setBuiltIn("1");
        sysmenu.setStatus("1");
        sysmenu.setLevel("-1".equals(sysmenu.getParentId())?1:2);
        return mapper.insert(sysmenu)>0?sysmenu:null;
    }

    @Override
    public SysMenu editSysMenu(SysMenu sysmenu) {
        return mapper.update(sysmenu)>0?sysmenu:null;
    }

    @Override
    public boolean checkName(SysMenu sysmenu) {
        int count = mapper.checkName(sysmenu.getName(),sysmenu.getParentId(),sysmenu.getId());
        return count > 0;
    }

    @Override
    public boolean checkCode(SysMenu sysmenu) {
        int count = mapper.checkCode(sysmenu.getCode(),sysmenu.getId());
        return count > 0;
    }

    @Override
    public Map<String, Object> removeSysMenu(String[] ids) {
        List<String> idarray = Arrays.asList(ids);
        //1.校验是否有子节点
        int count = mapper.countChildrenByIds(idarray);
        Map<String,Object> result = new HashMap<>();
        if(count>0){
            result.put("flag",false);
            result.put("msg","hasChildren");
            return result;
        }
        count = mapper.delete(idarray);
        if(count>0){
            result.put("flag",true);
        }else{
            result.put("flag",false);
            result.put("msg","removeError");
        }
        return result;
    }

    private List<SysMenu> buildTree(List<SysMenu> menus,String parentId){
        List<SysMenu> results = new ArrayList<>();
        for(SysMenu menu : menus){
            if((parentId==null && menu.getParentId()==null)||(parentId!=null && parentId.equals(menu.getParentId()))){
                menu.setChildren(buildTree(menus,menu.getId()));
               results.add(menu);
            }
        }
        return results;
    }
}

