package cn.gmuni.enrollment.sysmenu.service;

import cn.gmuni.enrollment.sysmenu.model.SysMenu;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ISysMenuService {
    List<SysMenu> listAllSysMenu();

    PageInfo<SysMenu> listAllSysMenuByPid(Map<String,Object> params);


    SysMenu saveSysMenu(SysMenu sysmenu);

    SysMenu editSysMenu(SysMenu sysmenu);


    boolean checkName(SysMenu sysmenu);

    boolean checkCode(SysMenu sysmenu);

    Map<String,Object> removeSysMenu(String[] ids);
}
