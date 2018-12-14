package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.dao.MenuRepository;
import cn.gmuni.zsgl.entity.Menu;
import cn.gmuni.zsgl.entity.MenuTree;
import cn.gmuni.zsgl.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


/**
 * @Author:ZhuXin
 * @Description: 菜单业务类
 * @Date:Create in 11:19 2018/5/8
 * @Modified By:
 **/
@Transactional
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    /**
     * 根据code查询菜单
     *
     * @param code
     * @return
     */
    @Override
    public Menu getMenuByCode(String code) {
        if (!StringUtils.isEmpty(code)) {
            return menuRepository.getByCode(code);
        } else {
            return null;
        }
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    @Override
    public List<Menu> listMenus(String status) {
        if (!StringUtils.isEmpty(status)) {
            return menuRepository.findAll(status);
        } else {
            return null;
        }
    }

    /**
     * 根据菜单层级获取菜单
     *
     * @param level
     * @return
     */
    @Override
    public List<Menu> findByLevel(Integer level) {
        if (level == 1) {
            return menuRepository.findByLevel(level);
        } else if (level == 2) {
            return menuRepository.findByLevel(level);
        } else {
            return null;
        }
    }

    /**
     * 通过菜单parentId查询子菜单
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Menu> findByParentId(String parentId) {
        if (!StringUtils.isEmpty(parentId)) {
            return menuRepository.findByParentId(parentId);
        } else {
            return null;
        }
    }

    /**
     * 获取一级菜单
     *
     * @return
     */
    @Override
    public List<Menu> getFirstMenu(String status) {
        if (!StringUtils.isEmpty(status)) {
            return menuRepository.getFirstMenu(status);
        } else {
            return null;
        }
    }

    /**
     * 获取子菜单
     *
     * @return
     */
    @Override
    public List<Menu> getSecondMenu(String status) {
        if (!StringUtils.isEmpty(status)) {
            return menuRepository.getSecondMenu(status);
        } else {
            return null;
        }
    }

    /**
     * 获取菜单树形结构
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> menuTree(String status) {
        MenuTree menuTree = new MenuTree();
        if (!StringUtils.isEmpty(status)) {
            List<Menu> menuList = this.listMenus(status);
            List<Map<String, Object>> mapList = menuTree.menuList(menuList);
            return mapList;
        } else {
            return null;
        }

    }

    /**
     * 菜单点击次数自增
     *
     * @param code
     * @return
     */
    @Override
    public Menu clickAdd(String code) {
        if (!StringUtils.isEmpty(code)) {
            Integer integer = menuRepository.updateClickAdd(code);
            return menuRepository.getMenuById(code);
        } else {
            return null;
        }

    }
}
