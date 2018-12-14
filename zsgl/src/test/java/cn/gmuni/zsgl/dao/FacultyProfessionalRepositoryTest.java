package cn.gmuni.zsgl.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create in 15:50 2018/5/28
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacultyProfessionalRepositoryTest {

    @Autowired
    FacultyProfessionalRepository facultyProfessionalRepository;

    /**
     * 获取学科专业列表
     */
    @Test
    public void getFaps(){
        List<Map> maps = facultyProfessionalRepository.listFaps();
        List<Map> res = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            Map map = new HashMap<>(maps.get(i));
            if("xy".equals(map.get("faculty"))){
                List<Map> children =new ArrayList<>();
                for (int j = 0; j < maps.size(); j++) {
                    Map m = maps.get(j);
                    if(!m.get("dLevel_code").equals(map.get("dLevel_code"))
                            && m.get("dLevel_code").toString().indexOf(map.get("dLevel_code").toString()) == 0){
                        children.add(m);
                    }
                }
                map.put("children", children);
                res.add(map);
            }
        }

        for (Map<String,Object> temp:res){
            for (Map.Entry<String,Object> map:temp.entrySet()){
                 System.out.println(map.getKey()+"____"+map.getValue());
            }
        }
    }

    /**
     * 获取专业列表
     */
    @Test
    public void getFa(){
        List<Map> maps = facultyProfessionalRepository.listFasOrMajor("xk");
        System.out.println(maps.size());
    }


    /**
     * 根据status招生状态获取专业列表
     */
    @Test
   public  void getByStatus(){
        List<Map> maps = facultyProfessionalRepository.listFapsByStatus("0");
        System.out.println(maps.size());
    }

    /**
     * 根据type类型和招生状态获取学科专业列表
     */
    @Test
    public void getByTypeAndStatus(){
        List<Map> maps = facultyProfessionalRepository.listFapsByTypeAndStatus("wk", "1");
        System.out.println(maps.size());
    }

    /**
     * 根据id查询
     */
    @Test
    public void getByFapid(){
        Map maps = facultyProfessionalRepository.getByFapId("4b69fb975a40493e97c0350f882fe398");
        System.out.println(maps.entrySet().toString());
    }


}