package cn.gmuni.zsgl.entity;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 菜单树形结构封装类
 * @Date:Create in 15:24 2018/5/16
 * @Modified By:
 **/


public class MenuTree {

    public static Map<String, Object> mapArray = new LinkedHashMap<String, Object>();
    public List<Menu> menusList;
    public List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    /**
     * 获取父级菜单以及所有子菜单
     *
     * @param menus
     * @return
     */
    public List<Map<String, Object>> menuList(List<Menu> menus) {
        this.menusList = menus;
        for (Menu x : menus) {
            Map<String, Object> mapSub = new LinkedHashMap<String, Object>();
            if (x.getLevel() == 1) {
                mapSub.put("id", x.getId());
                mapSub.put("name", x.getName());
                mapSub.put("code", x.getCode());
                mapSub.put("level", x.getLevel());
                mapSub.put("parentId", x.getParentId());
                mapSub.put("type", x.getType());
                mapSub.put("clickThrough", x.getClickThrough());
                mapSub.put("status", x.getStatus());
                mapSub.put("url", x.getUrl());
                mapSub.put("builtIn", x.getBuiltIn());
                mapSub.put("sort", x.getSort());
                mapSub.put("children", menuChild(x.getId()));
                list.add(mapSub);
            }
        }
        return list;
    }


    /**
     * 通过父级菜单的id获取所有的子菜单
     * 父级菜单的id和子菜单的parentId相等
     *
     * @param id
     * @return
     */
    public List<?> menuChild(String id) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        for (Menu temp : menusList) {
            Map<String, Object> childArray = new LinkedHashMap<String, Object>();
            if (id.equals(temp.getParentId())) {
                childArray.put("id", temp.getId());
                childArray.put("name", temp.getName());
                childArray.put("code", temp.getCode());
                childArray.put("level", temp.getLevel());
                childArray.put("parentId", temp.getParentId());
                childArray.put("type", temp.getType());
                childArray.put("clickThrough", temp.getClickThrough());
                childArray.put("status", temp.getStatus());
                childArray.put("url", temp.getUrl());
                childArray.put("builtIn", temp.getBuiltIn());
                childArray.put("sort", temp.getSort());
                childArray.put("children", menuChild(temp.getCode()));
                lists.add(childArray);
            }
        }
        return lists;
    }

}
