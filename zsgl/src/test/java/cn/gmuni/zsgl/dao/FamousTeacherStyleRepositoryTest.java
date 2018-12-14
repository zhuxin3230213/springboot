package cn.gmuni.zsgl.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Map;


/**
 * @Author:ZhuXin
 * @Description:
 * FamousTeacherStyleRepositoryTest
 * @Date:Create in 15:00 2018/6/14
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class FamousTeacherStyleRepositoryTest {

    @Autowired
    FamousTeacherStyleRepository famousTeacherStyleRepository;

    /**
     * 根据职称获取
     */
    @Test
    public void listByTitle() {
        Pageable pageable = PageRequest.of(0,2);
        Page<Map> page = famousTeacherStyleRepository.listByTitle("professor", pageable);
        for (Map temp:page){
            System.out.println(temp.toString());
        }
    }


    /**
     * 根据荣誉获取
     */
    @Test
    public void  listByHonor(){
        Pageable pageable = PageRequest.of(0,3);
        Page<Map> page = famousTeacherStyleRepository.listByHonor("4", pageable);
        for (Map temp:page){
            System.out.println(temp.toString());
        }
    }


    /**
     * 根据职称和荣誉获取分页
     */
    @Test
    public void listByTitleAndHonor(){
        Pageable pageable = PageRequest.of(0,1);
        Page<Map> page = famousTeacherStyleRepository.listByTitleAndHonor("教授", "长江学者", pageable);
        for (Map temp:page){
            System.out.println(temp.toString());
        }

    }


    /**
     * 根据姓名模糊查询
     */
    @Test
    public void listByName(){
        Pageable pageable = PageRequest.of(0,2);
        Page<Map> page = famousTeacherStyleRepository.listByName("张", pageable);
        for (Map temp:page){
            System.out.println(temp.toString());
        }

    }

    /**
     * 获取职称、荣誉列表
     */
    @Test
    public void listTitle(){
        List<Map> listTitle = famousTeacherStyleRepository.getListTitle();
        List<Map> listHonor = famousTeacherStyleRepository.getListHonor();
        System.out.println(listHonor.size());


    }


    /**
     * 获取名师风采列表
     */
    @Test
    public void listTest(){
        List<Map> list = famousTeacherStyleRepository.listFates();
        System.out.println(list.size());
    }

}