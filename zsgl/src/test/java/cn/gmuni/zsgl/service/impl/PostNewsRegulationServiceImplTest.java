package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.service.PostNewsRegulationService;
import cn.gmuni.zsgl.entity.PostNewsRegulation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author:ZhuXin
 * @Description:
 * PostNewsRegulationServiceImplTest
 * @Date:Create in 18:42 2018/5/29
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostNewsRegulationServiceImplTest {

    @Autowired
    @Qualifier("postNewsRegulationServiceImpl")
    PostNewsRegulationService postNewsRegulationService;


    /**
     *根据type类型获取分页
     */
    @Test
    public void getPageByType() {
        Page<PostNewsRegulation> pageByType = postNewsRegulationService.getByTypeNamePage("0", 1, 4);
        for (PostNewsRegulation temp:pageByType){
            System.out.println(temp.toString());
        }
        System.out.println(pageByType.getTotalElements());
    }
}