package cn.gmuni.sc.college.cache;

import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.college.model.College;
import cn.gmuni.sc.college.service.ICollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CollegeCache {

    @Autowired
    private ICollegeService collegeService;

    public static final String COLLEGE_INFO_CACHE = "COLLEGE_INFO_CACHE";


    public static List<College> list(){
        return RedisCacheUtils.build().get(COLLEGE_INFO_CACHE,List.class);
    }


    public static List<College> getAllCollege(){
        return  RedisCacheUtils.build().get(COLLEGE_INFO_CACHE,List.class);
    }

    @PostConstruct
    public void init(){

        List<College> cs = collegeService.listAll();
        if(cs!=null){
            RedisCacheUtils.build().put(COLLEGE_INFO_CACHE,cs);
        }
    }

    //新增后在缓存中添加
    public static void addCollege(College college){
        List<College> list = list();
        list.add(college);
        RedisCacheUtils.build().put(COLLEGE_INFO_CACHE,list);
    }

    //修改后在缓存中修改
    public static void updateCollege(College college){
        List<College> list = list();
        for (int i = 0; i < list.size() ; i++) {
            //判断缓存中的id与传入的id一致时
            if (college.getId().equals(list.get(i).getId())){
                 list.remove(i);
                 list.add(college);
                 break;
            }
        }
        RedisCacheUtils.build().put(COLLEGE_INFO_CACHE,list);
    }
    //根据学校编码获取学校对象
    public static College getCollege(String code){
        List<College> list = list();
        for (int i = 0; i < list.size() ; i++) {
            //判断缓存中的id与传入的id一致时
            College c = list.get(i);
            if (code.equals(c.getCode())){
                return c;
            }
        }
        return null;
    }

    //删除后在缓存中删除
    public static void delCollege(List<String> ids){
        List<College> list = list();
        for (int i = 0; i < ids.size() ; i++) {
            String id = ids.get(i);
            for (int j = 0; j < list.size() ; j++) {
                if (id.equals(list.get(j).getId())){
                    list.remove(j);
                    break;
                }
            }

        }

        RedisCacheUtils.build().put(COLLEGE_INFO_CACHE,list);
    }
}
