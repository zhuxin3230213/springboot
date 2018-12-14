package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.UpdateClickThrough;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * @Author:ZhuXin
 * @Description:
 * UpdateClickThroughRepositoryTest
 * @Date:Create in 14:15 2018/7/19
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateClickThroughRepositoryTest {

    @Autowired
    UpdateClickThroughRepository updateClickThroughRepository;

    /**
     *更新次数
     */
    @Test
    public void test() {
        Integer integer = updateClickThroughRepository.updateClickThrough("33270aba842641c4bb3c0dc23f37a7ed", "1");
        UpdateClickThrough updateClickThrough = updateClickThroughRepository.getByArticleIdAndType("33270aba842641c4bb3c0dc23f37a7ed", "1");
        System.out.println(updateClickThrough.getClickThrough());

    }
}