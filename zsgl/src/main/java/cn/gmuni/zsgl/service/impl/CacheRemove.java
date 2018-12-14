package cn.gmuni.zsgl.service.impl;


import cn.gmuni.zsgl.cache.FooterCache;
import cn.gmuni.zsgl.cache.HeaderCache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

/**
 * @Author:ZhuXin
 * @Description: 缓存清除策略
 * @Date:Create in 17:01 2018/7/17
 * @Modified By:
 **/


@Transactional
@Service
public class CacheRemove {

    private static final CacheManager cacheManager = CacheManager.getInstance();



    public void removeCache(String cacheNames) {
        Ehcache ehcache = cacheManager.getEhcache(cacheNames);
        ehcache.removeAll();
        if ("menus".equals(cacheNames)){
            HeaderCache.updateCache();
        }
        if ("configs".equals(cacheNames)){
            FooterCache.updateCache();
        }

        ehcache.flush();
    }
}

