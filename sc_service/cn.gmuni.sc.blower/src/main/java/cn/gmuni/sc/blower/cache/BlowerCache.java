package cn.gmuni.sc.blower.cache;

import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.college.model.College;
import cn.gmuni.sc.device.controller.BlowerDervice;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.gmuni.sc.college.cache.CollegeCache.COLLEGE_INFO_CACHE;

public class BlowerCache {

    public static final String BLOWER_CLOSE_CACHE = "BLOWER_CLOSE_CACHE";

    public static boolean BLOWER_CLOSE_RUN = false;

    @PostConstruct
    public void init(){
        List<Map<String,Object>> blowerCloseMap =new ArrayList<>();
        RedisCacheUtils.build().put(BLOWER_CLOSE_CACHE,blowerCloseMap);
    }

    public static List<Map<String,Object>> list(){
        return RedisCacheUtils.build().get(BLOWER_CLOSE_CACHE,List.class);
    }

    //新增后在缓存中添加
    public static void addBlowerClose(Map<String,Object> blowerClose){
        List<Map<String,Object>> list =list();
        for (Map<String,Object> map:list) {
            if (map.get("blowerCode").equals(blowerClose.get("blowerCode"))){
                return;
            }
            if (BLOWER_CLOSE_RUN == false){
                blowerClose();
            }
        }
        list.add(blowerClose);
        RedisCacheUtils.build().put(COLLEGE_INFO_CACHE,list);
    }


    //删除后在缓存中删除关闭的
    public static void delBlowerClose(Map<String,Object> blowerClose){
        List<Map<String,Object>> list =list();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("blowerCode").equals(blowerClose.get("blowerCode"))){
               list.remove(i);
               i--;
            }
        }
        RedisCacheUtils.build().put(COLLEGE_INFO_CACHE,list);
    }

    //吹风机关闭
    public static void blowerClose(){
        BLOWER_CLOSE_RUN = true;
        List<Map<String,Object>> list =list();
        for (int i = 0; i < list.size() ; i++) {
            Map res = new BlowerDervice().requestDevice("close",list.get(i));
            if (res.get("flag").equals("true")){
                BlowerCache.delBlowerClose(list.get(i));
                i--;
            }
        }
        if (list().size()==0){
            BLOWER_CLOSE_RUN = false;
            return;
        }else {
            try {
                Object.class.wait(1000*10);
                blowerClose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
