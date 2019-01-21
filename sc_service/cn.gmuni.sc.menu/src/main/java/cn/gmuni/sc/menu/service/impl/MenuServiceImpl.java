package cn.gmuni.sc.menu.service.impl;

import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.menu.mapper.MenuMapper;
import cn.gmuni.sc.menu.service.IMenuService;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    MenuMapper menuMapper;
    @Override
    public BaseResponse<Map<String, Object>> getMenuItem(Map<String, String> params) {
        BaseResponse response = new BaseResponse();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        String schoolCode = loginUser.getSchool();
        String module = params.get("module");
        String key = "menu" + schoolCode + module;
        RedisCacheUtils cacheUtils = RedisCacheUtils.build();
        Map<String, Object> res = null;
//        if(cacheUtils.hasKey(key)){
//            //优先从缓存中去出菜单
//            res = cacheUtils.get(key, Map.class);
//            cacheUtils.put(key, res);
//            response.setData(res);
//        }else{
            res = new HashMap<>();
            res.put("pay", new ArrayList<>());
            res.put("bill", new ArrayList<>());
            res.put("service", new ArrayList<>());
            //若缓存中不存在，则从数据库中查询
            List<Map<String, String>> items = menuMapper.getMenuItemByModule(module);
            Map<String, String> param = new HashMap<>();
            param.put("module", module);
            param.put("schooleCode", schoolCode);
            //获取自定义的菜单项
            List<Map<String, String>> customItems = menuMapper.getCustomMenuItemByModule(param);
            if(customItems.size() > 0){
                //若有自定义的菜单项，则应用自定义
                for (int i = 0; i < customItems.size(); i++) {
                    Map<String, String> customItem = customItems.get(i);
                    for (int j = 0; j < items.size(); j++) {
                        Map<String, String> item = items.get(j);
                        if(item.get("id").equals(customItem.get("menuId"))){
                            item.put("url", customItem.get("url"));
                            item.put("icon", customItem.get("icon"));
                            break;
                        }
                    }
                }
            }
            //将数据构建成树
            for (int i = 0; i < items.size(); i++) {
                Map<String, String> item = items.get(i);
                if(res.containsKey(item.get("menuGroup"))){
                    ((List)res.get(item.get("menuGroup"))).add(item);
                }
            }
            cacheUtils.put(key, res);
            response.setData(res);
//        }
        return response;
    }
}
