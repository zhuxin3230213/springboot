package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.service.EnrollmentPlanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create in 17:39 2018/8/2
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnrollmentPlanServiceImplTest {

    @Autowired
    EnrollmentPlanService enrollmentPlanService;
    @Test
    public void listEnPlansByYearAndProvinces() {
        List<Map> list = enrollmentPlanService.listEnPlansByYearAndProvinces(2017, "610000");
        for (Map<String,Object> temp:list){
           for (Map.Entry<String,Object> map:temp.entrySet()){
               if ("edu_systme".equals(map.getKey())){
                  map.setValue(map.getValue().toString());
               }
           }
        }
    }
}