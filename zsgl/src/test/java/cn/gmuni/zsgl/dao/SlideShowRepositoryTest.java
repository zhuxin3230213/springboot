package cn.gmuni.zsgl.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Map;



/**
 * @Author:ZhuXin
 * @Description:
 * SlideShowRepositoryTest
 * @Date:Create in 14:29 2018/6/29
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class SlideShowRepositoryTest {

    @Autowired
    SlideShowRepository slideShowRepository;


    /**
     * 获取轮播图列表
     */
    @Test
    public void listSlideShows() {
        List<Map> slideShows = slideShowRepository.listSlideShows();
        System.out.println(slideShows.size());
    }
}