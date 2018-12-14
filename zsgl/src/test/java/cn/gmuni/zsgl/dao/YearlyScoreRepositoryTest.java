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
 * @Date:Create in 16:28 2018/6/8
 * @Modified By:
 **/


@RunWith(SpringRunner.class)
@SpringBootTest
public class YearlyScoreRepositoryTest {


    @Autowired
    YearlyScoreRepository yearlyScoreRepository;


    /**
     * 获取历年分数列表
     */
    @Test
    public void listYearlyScores() {
        List<Map> maps = yearlyScoreRepository.listYearlyScores();
        System.out.println(maps.size());
    }

    /**
     * 根据年份获取分数列表
     */
    @Test
    public void listYearlyScoresByYear(){
        List<Map> maps = yearlyScoreRepository.listYearlyScoreByYear(2017);
        System.out.println(maps.size());
    }

    /**
     * 根据省份获取分数列表
     */
    @Test
    public void listYearlyScoreByProvinces(){
        List<Map> maps = yearlyScoreRepository.listYearlyScoreByProvinces("110000");
        System.out.println(maps.size());
    }

    /**
     * 根据年份和省份获取历年分数
     */
    @Test
    public void listByYearAndProvinces(){
        List<Map> maps = yearlyScoreRepository.listByYearAndProvinces(2017, "110000");
        System.out.println(maps.size());
    }

    /**
     * 根据id查询历年分数
     */
    @Test
    public void getById(){
        Map map = yearlyScoreRepository.getById("1");
        System.out.println(map.entrySet().toString());
    }
}