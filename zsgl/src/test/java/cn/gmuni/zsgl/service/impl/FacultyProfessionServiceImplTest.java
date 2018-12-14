package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.service.FacultyProfessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description:
 *FacultyProfessionServiceImplTest
 * @Date:Create in 11:30 2018/5/29
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacultyProfessionServiceImplTest {


    @Autowired
    @Qualifier("facultyProfessionServiceImpl")
    FacultyProfessionService facultyProfessionService;


    /**
     * 根据状态获取列表
     */
    @Test
    public void listFapsByStatus(){
        List<Map> maps = facultyProfessionService.facpTreeByStatus("1");
         for (Map<String,Object> temp:maps){
             System.out.println(temp);
         }
    }


    /**
     * 根据type类型和状态获取列表
     */
     @Test
     public void listFapsByTypeAndStatus(){
         List<Map> maps = facultyProfessionService.facpTreeByTypeAndStatus("wk", "0");
         for (Map temp : maps) {
             System.out.println(temp);
         }

     }

}