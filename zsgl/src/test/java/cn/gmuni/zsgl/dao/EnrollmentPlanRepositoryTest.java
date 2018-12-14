package cn.gmuni.zsgl.dao;


import cn.gmuni.zsgl.entity.EnrollmentPlan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;
import java.util.Map;



/**
 * @Author:ZhuXin
 * @Description:
 * EnrollmentPlanRepositoryTest
 * @Date:Create in 15:58 2018/6/4
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnrollmentPlanRepositoryTest {

    @Autowired
    EnrollmentPlanRepository enrollmentPlanRepository;


    /**
     *获取招生计划列表
     */
    @Test
    public void getEnrollmentPlans(){
        List<Map> maps = enrollmentPlanRepository.listEnrollmentPlans();
        System.out.println(maps.size());
    }

    /**
     * 根据年份查询招生计划
     */
    @Test
    public void getEnrollmentPlansByYear(){
        List<Map> maps = enrollmentPlanRepository.listEnrollmentPlansByYear(2017);
        System.out.println(maps.size());
    }

    /**
     * 根据省份查询招生计划
     */
    @Test
    public void getEnrollmentPlansByProvinces(){
        List<Map> maps = enrollmentPlanRepository.listEnrollmentPlansByProvinces("110000");
        System.out.println(maps.size());
    }

    /**
     * 根据年份和省份查询招生计划
     */
    @Test
    public void getEnrollmentPlansByYearAndProvinces(){
        Integer year =null;
        Calendar a =Calendar.getInstance();
       year= a.get(Calendar.YEAR) -1;
        System.out.println(year);
        List<Map> maps = enrollmentPlanRepository.listEnrollmentPlansByYearAndProvinces(year, "110000");
        System.out.println(maps.size());
    }

    /**
     * 根据id查询招生计划
     */
    @Test
    public void getById(){
        Map map = enrollmentPlanRepository.getById("1");
        System.out.println(map.entrySet().toString());
    }


    /**
     * 获取省份列表
     */
    @Test
    public void getProvinces(){
        List<Map> maps = enrollmentPlanRepository.listProvinces();
        System.out.println(maps.size());
    }
}