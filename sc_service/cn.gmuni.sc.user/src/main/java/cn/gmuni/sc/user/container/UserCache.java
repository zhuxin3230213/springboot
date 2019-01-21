package cn.gmuni.sc.user.container;

import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.service.IThirdUserService;
import cn.gmuni.sc.utils.GmBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserCache {
    @Autowired
    @Qualifier(value = "thirdUserServiceImpl")
    IThirdUserService userService;

    private static final String THIRD_PART_USER_INFO_CACHE = "THIRD_PART_USER_INFO_CACHE";

    private static final String THIRD_PART_USER_CACHE = "THIRD_PART_USER_CACHE";



    public static Map<String, ThirdPartUserInfo> getAllUserInfo(){
        return RedisCacheUtils.build().get(THIRD_PART_USER_INFO_CACHE,Map.class);
    }

    public static Map<String,ThirdPartUser> getAllUser(){
        return RedisCacheUtils.build().get(THIRD_PART_USER_CACHE,Map.class);
    }

    public static ThirdPartUserInfo getUserInfoByKey(String key){
        return getAllUserInfo().get(key);
    }

    public static ThirdPartUser getUserById(String id){
        return getAllUser().get(id);
    }

    public static void addUser(ThirdPartUser user){
        Map<String,ThirdPartUser> users = getAllUser();
        users.put(user.getId(),user);
        RedisCacheUtils.build().put(THIRD_PART_USER_CACHE,users);
    }

    public static void updateUser(ThirdPartUser user){
        Map<String,ThirdPartUser> users = getAllUser();
        ThirdPartUser u = users.get(user.getId());
        if(u!=null){
            GmBeanUtils.copyPropertiesIgnoreNull(user,u);
            users.put(user.getId(),u);
            RedisCacheUtils.build().put(THIRD_PART_USER_CACHE,users);
        }else{
            addUser(user);
        }
    }

    public static void removeUser(String id){
        Map<String,ThirdPartUser> users = getAllUser();
        users.remove(id);
        RedisCacheUtils.build().put(THIRD_PART_USER_CACHE,users);
    }

    public static void addUserInfo(ThirdPartUserInfo info){
        Map<String,ThirdPartUserInfo> userInfos = getAllUserInfo();
        userInfos.put(info.getIndentifier()+"-"+info.getCredential(),info);
        RedisCacheUtils.build().put(THIRD_PART_USER_INFO_CACHE,userInfos);
    }

    public static void logout(String token) {
        Map<String,ThirdPartUserInfo> userInfos = getAllUserInfo();
        ThirdPartUserInfo userInfo = userInfos.get(token);
        if(userInfo!=null){
            userInfo.setState("0");
        }
        RedisCacheUtils.build().put(THIRD_PART_USER_INFO_CACHE,userInfos);
    }

    @PostConstruct
    public  void init(){
        Map<String, ThirdPartUserInfo> userInfos = new HashMap<>();
        Map<String, ThirdPartUser> users = new HashMap<>();
        List<ThirdPartUserInfo> thirdPartUserInfos = userService.listAllUserInfo();
        if(thirdPartUserInfos!=null && thirdPartUserInfos.size()>0){
            for (ThirdPartUserInfo info : thirdPartUserInfos){
                userInfos.put(info.getIndentifier()+"-"+info.getCredential(),info);
            }
        }
        List<ThirdPartUser> thirdPartUsers = userService.listAllUser();
        if(thirdPartUsers!=null){
            for(ThirdPartUser user :thirdPartUsers){
                users.put(user.getId(),user);
            }
        }
        RedisCacheUtils.build().put(THIRD_PART_USER_INFO_CACHE,userInfos);
        RedisCacheUtils.build().put(THIRD_PART_USER_CACHE,users);
    }
}
