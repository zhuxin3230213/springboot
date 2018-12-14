package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.entity.FamousTeacherStyle;
import cn.gmuni.zsgl.service.FamousTeacherStyleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;


/**
 * @Author:ZhuXin
 * @Description:
 * FamousTeacherStyleServiceImplTest
 * @Date:Create in 16:34 2018/6/14
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class FamousTeacherStyleServiceImplTest {


    @Autowired
    @Qualifier("famousTeacherStyleServiceImpl")
    FamousTeacherStyleService famousTeacherStyleService;


    /**
     * 根据职称和荣誉查询
     */
    @Test
    public void listByTitleAndHonorPage() {
        Page<Map> page = famousTeacherStyleService.listByTitleAndHonorPage("professor", "", "", 0,10);
        for (Map<String ,Object> temp:page){
            for (Map.Entry<String,Object> map:temp.entrySet()){
                System.out.println(map.getKey()+"________"+map.getValue());
            }
        }
    }

    /**
     * 根据id获取教师所有信息
     */
    @Test
    public  void getFaById() {
       //Map map = famousTeacherStyleService.getByFaId("1");
       //System.out.println(map.get("teacher_introduction"));
        FamousTeacherStyle byId = famousTeacherStyleService.getById("1");
        System.out.println(byId.getTeacherIntroduction());

    }
}