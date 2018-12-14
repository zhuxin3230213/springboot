package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.entity.ProfessionContent;
import cn.gmuni.zsgl.service.ProfessionContentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * @Author:ZhuXin
 * @Description:
 *ProfessionContentServiceImplTest
 * @Date:Create in 9:56 2018/7/2
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfessionContentServiceImplTest {


    @Autowired
    @Qualifier(value = "professionContentServiceImpl")
    ProfessionContentService professionContentService;

    /**
     * 根据学科专业的id获取对象
     */
    @Test
    public void getByProId() {
        ProfessionContent professionContent = professionContentService.getByProId("f01c6696345d42999af767de6b996ad6");
        System.out.println(professionContent);
    }
}