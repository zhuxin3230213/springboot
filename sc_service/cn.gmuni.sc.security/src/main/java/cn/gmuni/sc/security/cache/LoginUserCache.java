package cn.gmuni.sc.security.cache;

import cn.gmuni.sc.user.container.UserCache;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.service.IThirdUserService;
import cn.gmuni.sc.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 启动读取登录用户
 */
@Component
public class LoginUserCache {

    @Autowired
    @Qualifier(value = "thirdUserServiceImpl")
    IThirdUserService userService;

    /**
     *进行token 校验
     * @param token
     * @param identityType
     * @return
     */
    public static boolean verify(String token,int identityType){
        Claims claims = JwtUtils.parser(token);
        String identifier = claims.getSubject();
        ThirdPartUserInfo info = getUserInfo().get(identifier);
        if(info==null){
            return false;
        }
        if(!token.equals(info.getToken())){
            return false;
        }
        //已退出登录
        if("0".equals(info.getState())){
            return false;
        }
        return true;
    }

    public static ThirdPartUserInfo get(String token){
        Claims claims = JwtUtils.parser(token);
        String identifier = claims.getSubject();
        return getUserInfo().get(identifier);
    }

    /**
     * 添加新校验
     * @param info
     */
    public static void add(ThirdPartUserInfo info){
        UserCache.addUserInfo(info);
    }

    /**
     * 删除校验
     * @param token
     */
    public static void logout(String token){
        Claims claims = JwtUtils.parser(token);
        String identifier = claims.getSubject();
        if(identifier!=null){
            UserCache.logout(token);
        }
    }

    private static Map<String,ThirdPartUserInfo> getUserInfo(){
        return UserCache.getAllUserInfo();
    }


}
