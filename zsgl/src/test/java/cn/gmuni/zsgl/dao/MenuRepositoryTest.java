package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author:ZhuXin
 * @Description:
 * MenuRepositoryTest
 * @Date:Create in 15:27 2018/5/22
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuRepositoryTest {
    @Autowired
    MenuRepository menuRepository;

    /**
     * 菜单层级查询测试
     */
    @Test
    public void testLevel() {
       /* List<Menu> list =menuRepository.findByLevel(1);
        for (Menu temp:list){
            System.out.println("____________________________");
            System.out.println(temp.toString());
        }*/
     /*   List<Menu> secondMenu = menuRepository.getSecondMenu();
        for (Menu temp:secondMenu){
            System.out.println(temp.toString());
        }
*/
        List<Menu> firstMenu = menuRepository.getFirstMenu("1");
        for (Menu temp : firstMenu) {
            System.out.println(temp.toString());
        }

    }

    /**
     * Pageable来对数据库进行分页查询
     */
    @Test
    public void testPageAble() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<Menu> datas = menuRepository.findAll(pageable);
        System.out.println("总条数：" + datas.getTotalElements());
        System.out.println("总页数：" + datas.getTotalPages());
        for (Menu menu : datas) {
            System.out.println(menu);
        }
    }


    /**
     * 菜单点击次数自增
     */
    @Test
    public void testClickAdd() {
        int i = menuRepository.updateClickAdd("4");
        System.out.println(i);
    }


    /**
     * 根据code获取菜单
     */
    @Test
    public void getMenuByCode() {
        Menu menu = menuRepository.getByCode("1000");
        System.out.println(menu.getName());

    }
}