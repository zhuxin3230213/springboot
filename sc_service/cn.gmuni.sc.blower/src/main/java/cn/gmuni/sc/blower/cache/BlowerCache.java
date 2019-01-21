package cn.gmuni.sc.blower.cache;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.blower.mapper.BlowerMapper;
import cn.gmuni.sc.device.BlowerDervice;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlowerCache {

    public static final String BLOWER_CLOSE_CACHE = "BLOWER_CLOSE_CACHE";

    public static boolean BLOWER_CLOSE_RUN = false;

    @PostConstruct
    public void init(){
        RedisCacheUtils.build().put(BLOWER_CLOSE_CACHE,new ArrayList<>());
    }

    public static List<Map<String,Object>> list(){
        List<Map<String,Object>> list = RedisCacheUtils.build().get(BLOWER_CLOSE_CACHE,List.class);
        if (list==null){
            return new ArrayList<>();
        }
        return list;
    }

    //新增后在缓存中添加
    public static void addBlowerClose(Map<String,Object> blowerClose){
        List<Map<String,Object>> list =list();
        if (list.size() != 0 && list != null){
            for (Map<String,Object> map:list) {
                if (map.equals(blowerClose)){
                    return;
                }
            }
        }
        //添加缓存
        list.add(blowerClose);
        RedisCacheUtils.build().put(BLOWER_CLOSE_CACHE,list);
        //执行发送命令
        if (BLOWER_CLOSE_RUN == false){
            BLOWER_CLOSE_RUN = true;
            blowerClose();
        }

    }


    //删除后在缓存中删除关闭的
    public static void delBlowerClose(Map<String,Object> blowerClose){
        List<Map<String,Object>> list =list();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(blowerClose)){
               list.remove(i);
               i--;
            }
        }
        RedisCacheUtils.build().put(BLOWER_CLOSE_CACHE,list);
    }

    //吹风机关闭
    public static void blowerClose(){
       if (BLOWER_CLOSE_RUN == false){
           return;
       }
        List<Map<String,Object>> list =list();
        for (int i = 0; i < list.size() ; i++) {
            Map res = new BlowerDervice().requestDevice("close",list.get(i));
            if (res.get("flag").equals("true")){
                BlowerMapper blowerMapper = (BlowerMapper) ApplicationContextProvider.getBean("blowerMapper");
                blowerMapper.initStatus(list.get(i));
                BlowerCache.delBlowerClose(list.get(i));
            }
        }
        if (list().size() == 0){
            BLOWER_CLOSE_RUN = false;
            return;
        }else {
            try {
                TempThread.sleep(1000*10);
                blowerClose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //线程休眠10秒运行
    class TempThread extends Thread{
        @Override
        public void run() {
        }
    }

}
