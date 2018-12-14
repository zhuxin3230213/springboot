package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.Content;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description:
 *ContentRepositoryTest
 * @Date:Create in 18:18 2018/5/28
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentRepositoryTest {


    @Autowired
    ContentRepository contentRepository;



    @Test
     public void test(){
        Content content = contentRepository.getContentAdmissionsPolicy("9272575fd5564f898b05eaeb5498c3f5", "2000");
        System.out.println(content);
    }


   /**
     *获取分页
     */
    @Test
    public void getPage(){
        Sort sort =new Sort(Sort.Direction.DESC,"clickThrough");
        Pageable pageable = PageRequest.of(0,4,sort);
        Page<Content> byParentCode = contentRepository.getByParentCode("2001", pageable);
        for (Content temp:byParentCode){
            System.out.println(temp);
        }
    }

    /**
     * 热点信息排序
     */
    @Test
    public void getHosts(){
        Pageable pageable = PageRequest.of(0,3);
       // Page maps = contentRepository.getByClickThroughHosts(pageable);

        //System.out.println(maps.getTotalElements());
    }


    /**
     * 限制热点信息条数
     */
    @Test
    public void getByLimitHot(){
       List<Map> byLimitClickThroughHosts = contentRepository.getByLimitClickThroughHosts("2004",0, 4);
        System.out.println(byLimitClickThroughHosts.size());
    }

}