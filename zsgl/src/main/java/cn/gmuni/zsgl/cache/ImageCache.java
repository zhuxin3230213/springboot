package cn.gmuni.zsgl.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 图片缓存
 * @Date:Create in 16:20 2018/6/28
 * @Modified By:
 **/

public class ImageCache {
    private static Map<String, String> caches = new HashMap<>();

    public static boolean isCache(String id, String lastModified) {
        if (caches.containsKey(id)) {
            String l = caches.get(id);
            if (l.equals(lastModified)) {
                return true;
            }
        }
        caches.put(id, lastModified);
        return false;
    }

}
