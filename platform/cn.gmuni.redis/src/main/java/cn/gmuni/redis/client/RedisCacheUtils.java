package cn.gmuni.redis.client;

import cn.gmuni.base.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component(value = "redisCacheUtils")
public class RedisCacheUtils{

    @Autowired
    @Qualifier("redisTemplate")
    private  RedisTemplate template;

    private static RedisCacheUtils redisCacheUtils;

    @Autowired
    public RedisCacheUtils(RedisTemplate template){
        this.template = template;
    }

    /**
     * 添加缓存
     * @param key
     * @param data
     */
    public void put(String key,Object data){
            template.opsForValue().set(key,data);
    }

    /**
     * 添加缓存，可设置过期时间
     * @param key
     * @param time
     * @param data
     */
    public void put(String key,Object data,long time){
        template.opsForValue().set(key,data,time);
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public Object get(String key){
        if(key==null){
            return null;
        }
       return template.opsForValue().get(key);
    }

    /**
     * 根据指定数据类型获取数据
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    public <T>T get(String key,Class<T> t){
        return (T)template.opsForValue().get(key);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        return template.hasKey(key);
    }

    /**
     * 删除指定的key对应的缓存
     * @param key
     */
    public void delete(String... key){
        if(key!=null && key.length>0){
            if(key.length==1){
                template.delete(key[0]);
            }else{
                template.delete(Arrays.asList(key));
            }
        }
    }



    public static RedisCacheUtils build(){
        if(redisCacheUtils==null){
            synchronized (RedisCacheUtils.class){
                if(redisCacheUtils == null){
                    redisCacheUtils = (RedisCacheUtils) ApplicationContextProvider.getBean("redisCacheUtils");
                }
            }
        }
        return redisCacheUtils;
    }
}
