package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.entity.Menu;
import cn.gmuni.zsgl.entity.MenuTree;
import cn.gmuni.zsgl.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description:
 * MenuServiceImplTest
 * @Date:Create in 15:30 2018/5/22
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceImplTest {

    @Autowired
    MenuService menuService;

    /**
     *父节点查询子菜单测试
     */
    @Test
    public void testParentCode(){
        List<Menu> list = menuService.findByParentId("1001");
        for (Menu temp:list){
            System.out.println("******************************");
            System.out.println(temp.toString());
        }
    }

    /**
     *菜单树查询封装测试
     */
    @Test
    public void testMenuTree() {
        List<Menu> list = menuService.listMenus("1");
        //System.out.println(list);
        MenuTree menuTree = new MenuTree();
        List<Map<String,Object>> list1 = menuTree.menuList(list);
        for (Map<String,Object> m : list1) {
            Iterator<Map.Entry<String, Object>> it = m.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, Object> entry = it.next();
                System.out.println(entry.getKey()+"___"+entry.getValue());
            }
           /* if (temp != null) {
                Field[] fields = temp.getClass().getDeclaredFields();
                try {
                    for (Field f : fields) {
                        f.setAccessible(true);
                        String name = f.getName();
                        Object value = f.get(temp);
                        System.out.println(name + "--" + value.toString());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }



}