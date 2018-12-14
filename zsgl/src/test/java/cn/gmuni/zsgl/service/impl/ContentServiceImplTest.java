package cn.gmuni.zsgl.service.impl;


import cn.gmuni.zsgl.entity.Content;
import cn.gmuni.zsgl.service.ContentService;
import cn.gmuni.zsgl.service.PostNewsRegulationService;
import cn.gmuni.zsgl.util.GlobalSearchUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

/**
 * @Author:ZhuXin
 * @Description:
 * ContentServiceImplTest
 * @Date:Create in 15:24 2018/5/21
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentServiceImplTest {


    @Autowired
    ContentService contentService;
    @Autowired
    PostNewsRegulationService postNewsRegulationService;

    /**
     * 通过菜单id查询所有正文
     */
    @Test
    public void findContentByMenuId() {

        List<Content> contentByMenuId = contentService.findContentByMenuId("2000");
        for (Content temp:contentByMenuId){
            System.out.println(temp.toString());
        }
    }




    /**
     * 根据parentCode查询正文
     */
    @Test
    public void testContent(){
        List<Content> contents = contentService.listContents();
        for (Content temp:contents){
            System.out.println(temp.toString());
        }
       /* List<Content> byParentCode = contentService.findByParentCode("2000");
        for (Content temp:byParentCode){
            System.out.println(temp.toString());
        }*/
    }


    /**
     * 取正文中的几条
     */
    @Test
    public void testContentByLimit(){
        List<Content>  list = new ArrayList<Content>();
        List<Content> list1 = contentService.listContents();
        list =list1.subList(0,4);
        for (Content temp:list){
            System.out.println(temp.toString());
        }
    }


    /**
     * 根据院系专业的code查询正文
     */
    @Test
    public  void getContentFapCode(){
        Content content = contentService.getByFapId("f01c6696345d42999af767de6b996ad6");
        contentService.clickAdd(content.getId());
        System.out.println(content);
    }





    /**
     * 获取正文总记录数
     */
    @Test
    public void getCount(){
        //Integer count = contentService.getCount();
        //System.out.println(count);

        Integer hostsCount = contentService.getHostsCount();
        System.out.println(hostsCount);
    }


    /**
     * 获取总页数
     */
    @Test
    public void getTotalPages(){
       /* Integer countTotalPages = contentService.getCountTotalPages(0, 3);
        System.out.println(countTotalPages);*/

       // Page page = contentService.getByClickHosts(0, 3);
        //System.out.println(page.getTotalElements()+"___"+page.getTotalPages());
       /* Integer hostTotalPages = contentService.getHostTotalPages(0, 3);
        System.out.println(hostTotalPages);*/
    }

    /**
     * 获取全局搜索信息
     */
    @Test
    public void getSearchs(){
        List<GlobalSearchUtil> list = contentService.listSearchs("工", 1, 10);
        for (GlobalSearchUtil temp:list){
            System.out.println(temp);
        }
    }

    /**
     * 获取限制信息
     */
    @Test
    public  void getLimit(){
        List<Map> hosts = contentService.getByLimitHosts("2004",0,4);
        System.out.println(hosts.size());

    }

}