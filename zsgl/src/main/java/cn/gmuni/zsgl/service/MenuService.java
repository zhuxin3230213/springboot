package cn.gmuni.zsgl.service;

import cn.gmuni.zsgl.entity.Menu;


import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 招生系统一级菜单与二级菜单业务接口
 * @Date:Create in 11:04 2018/5/8
 * @Modified By:
 **/


public interface MenuService {

    /**
     * 根据code获取菜单的信息
     *
     * @param code
     * @return
     */
    Menu getMenuByCode(String code);

    /**
     * 获取菜单列表
     *
     * @return
     */
    List<Menu> listMenus(String status);

    /**
     * 通过菜单层级查询菜单
     *
     * @param level
     * @return
     */
    List<Menu> findByLevel(Integer level);

    /**
     * 通过parentId查询子菜单
     *
     * @param parentId
     * @return
     */
    List<Menu> findByParentId(String parentId);

    /**
     * 获取父级菜单
     *
     * @return
     */
    List<Menu> getFirstMenu(String status);

    /**
     * 获取子菜单
     *
     * @return
     */
    List<Menu> getSecondMenu(String ststus);

    /**
     * 获取菜单树形结构
     *
     * @return
     */
    List<Map<String, Object>> menuTree(String status);

    /**
     * 点击次数自增
     *
     * @param code
     * @return
     */
    Menu clickAdd(String code);
}
