package cn.gmuni.lookup.container;

import cn.gmuni.lookup.model.Lookup;
import cn.gmuni.lookup.model.LookupTree;
import cn.gmuni.lookup.service.ILookupService;
import cn.gmuni.redis.client.RedisCacheUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class LookupCache {

    @Autowired
    @Qualifier(value = "lookupServiceImpl")
    private ILookupService lookupService;

    private static final String LOOKUP_CACHE = "LOOKUP_CACHE";

    public static List<LookupTree> list() {
        return RedisCacheUtils.build().get(LOOKUP_CACHE,List.class);
    }

    public static void update(Lookup lookup) {
        String id = lookup.getId();
        if (lookup.getLevel() != 1) {
            id = lookup.getParentId();
        }
        List<LookupTree> trees = RedisCacheUtils.build().get(LOOKUP_CACHE,List.class);
        for (LookupTree lk : trees) {
            if (id.equals(lk.getId())) {
                if (lookup.getLevel() != 1) {
                    for (Lookup l : lk.getChildren()) {
                        if (l.getId().equals(lookup.getId())) {
                            BeanUtils.copyProperties(lookup, l);
                        }
                    }
                } else {
                    BeanUtils.copyProperties(lookup, lk, "children");
                }
            }
        }
        RedisCacheUtils.build().put(LOOKUP_CACHE,trees);
    }

    public static void add(Lookup lookup) {
        List<LookupTree> trees = RedisCacheUtils.build().get(LOOKUP_CACHE,List.class);
        if (lookup.getLevel() == 1) {
            LookupTree tree = new LookupTree();
            BeanUtils.copyProperties(lookup, tree);
            trees.add(tree);
        } else {
            String pid = lookup.getParentId();
            for (LookupTree lk : trees) {
                if (pid.equals(lk.getId())) {
                    LookupTree tree = new LookupTree();
                    BeanUtils.copyProperties(lookup, tree);
                    lk.getChildren().add(tree);
                    break;
                }
            }
        }
        RedisCacheUtils.build().put(LOOKUP_CACHE,trees);
    }

    public static void remove(List<String> ids) {
        List<LookupTree> trees = RedisCacheUtils.build().get(LOOKUP_CACHE,List.class);
        for(String id : ids){
            for(int i=0;i<trees.size();){
                LookupTree lk = trees.get(i);
                if(id.equals(lk.getId())){
                    trees.remove(i);
                    break;
                }else{
                    i++;
                    if(lk.getChildren().size()>0){
                        for(LookupTree l : lk.getChildren()){
                            if(id.equals(l.getId())){
                                lk.getChildren().remove(l);
                                break;
                            }
                        }
                    }
                }
            }
        }
        RedisCacheUtils.build().put(LOOKUP_CACHE,trees);
    }

    public static void updateStatus(List<String> ids, String status) {
        List<LookupTree> trees = RedisCacheUtils.build().get(LOOKUP_CACHE,List.class);
        for(String id : ids){
            for(int i=0;i<trees.size();){
                LookupTree lk = trees.get(i);
                if(id.equals(lk.getId())){
                    lk.setStatus(status);
                    break;
                }else{
                    i++;
                    if(lk.getChildren().size()>0){
                        for(LookupTree l : lk.getChildren()){
                            if(id.equals(l.getId())){
                                l.setStatus(status);
                                break;
                            }
                        }
                    }
                }
            }
        }
        RedisCacheUtils.build().put(LOOKUP_CACHE,trees);
    }


    @PostConstruct
    @Order(value = 5)
    public void init() {
        System.out.println("开始缓存字典");
        List<LookupTree> ts = lookupService.listTree();
        RedisCacheUtils.build().put(LOOKUP_CACHE,ts.get(0).getChildren());
        System.out.println("字典缓存完成");
    }
}
