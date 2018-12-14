package cn.gmuni.zsgl.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description:
 * PostNewsRegulationRepositoryTest
 * @Date:Create in 14:48 2018/5/30
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostNewsRegulationRepositoryTest {

     @Autowired
     PostNewsRegulationRepository postNewsRegulationRepository;







    @Test
    public  void test(){
        Pageable page = PageRequest.of(0,3);
        List<Map> byTypeName = postNewsRegulationRepository.getByTypeNameLimit("0", page);
        System.out.println(byTypeName.size());
    }

    @Test
    public void test1(){
        Integer pnrCountByType = postNewsRegulationRepository.getPNRCountByType("0");
        System.out.println(pnrCountByType);
    }
}