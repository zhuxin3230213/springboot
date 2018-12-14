package cn.gmuni.sc.pay.cache;

import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.pay.model.NetPackage;
import cn.gmuni.sc.pay.service.INetPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NetPackageCache {

    public static final String NET_PACKAGE_CACHE = "NET_PACKAGE_CACHE";

    @Autowired
    @Qualifier("netPackageServiceImpl")
    INetPackageService netPackageServiceImpl;

    //初始化
    @PostConstruct
    public void init(){
        Map<String,Object> params = new HashMap<>();
        List<NetPackage> list = netPackageServiceImpl.getNetPackage(params);
        if (list != null){
            Map<String,ArrayList> maps = new HashMap<>();
            for(NetPackage p : list){
                String school = p.getSchoolCode();
                if(!maps.containsKey(school)){
                    maps.put(school,new ArrayList<>());
                }
                maps.get(school).add(p);
            }
            RedisCacheUtils.build().put(NET_PACKAGE_CACHE,maps);
        }

    }

    //添加数据
    public static void add(NetPackage netPackage){
       Map<String,ArrayList> maps = (Map<String, ArrayList>) RedisCacheUtils.build().get(NET_PACKAGE_CACHE);
        List<NetPackage> netPackages = (List<NetPackage>) maps.get(netPackage.getSchoolCode());
       if (netPackages!= null){
          netPackages.add(netPackage);
       }else{
           List<NetPackage> list =  new ArrayList<>();
           list.add(netPackage);
           maps.put(netPackage.getSchoolCode(),(ArrayList)list);
       }
       RedisCacheUtils.build().put(NET_PACKAGE_CACHE,maps);
    }

    //修改数据
    public static void update(NetPackage netPackage){
        Map<String,ArrayList> maps = (Map<String, ArrayList>) RedisCacheUtils.build().get(NET_PACKAGE_CACHE);
        //删除缓存中原有数据
        for (String code:maps.keySet()) {
            List<NetPackage> netPackages =  (List<NetPackage>) maps.get(code);
            for (int i = 0; i <netPackages.size() ; i++) {
                 if (netPackage.getId().equals(netPackages.get(i).getId())){
                     netPackages.remove(i);
                     netPackages.add(netPackage);
                     break;
                 }
            }
        }
        RedisCacheUtils.build().put(NET_PACKAGE_CACHE,maps);
    }

    //删除
    public static void del(List<String> ids){
        Map<String,ArrayList> maps = (Map<String, ArrayList>) RedisCacheUtils.build().get(NET_PACKAGE_CACHE);
        for(Map.Entry<String,ArrayList> entry : maps.entrySet()){
            List<NetPackage> list = entry.getValue();
            for(int i=0;i<list.size();){
                NetPackage p = list.get(i);
                if(ids.contains(p.getId())){
                    list.remove(i);
                }else{
                    i++;
                }
            }
        }
        RedisCacheUtils.build().put(NET_PACKAGE_CACHE,maps);
    }
}
