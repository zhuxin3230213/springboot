package cn.gmuni.sc.user.utils;

import cn.gmuni.sc.user.container.UserCache;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUtils {

    /**
     * 获取所有登录用户信息
     * @return
     */
    public static List<ThirdPartUserInfo> listAllUserInfo(){
        Map<String, ThirdPartUserInfo> allUserInfo = UserCache.getAllUserInfo();
        List<ThirdPartUserInfo> result = new ArrayList<>();
        for(Map.Entry<String, ThirdPartUserInfo> m : allUserInfo.entrySet()){
            result.add(m.getValue());
        }
        return result;
    }

    /**
     * 获取登录用户信息
     * @return
     */
    public static ThirdPartUser getLoginUser(){
        return getLoginUser(getRequest());
    }

    public static ThirdPartUser getLoginUser(HttpServletRequest request){
        ThirdPartUserInfo userInfoByKey = getLoginUserInfo(request);
        if(userInfoByKey!=null){
            return UserCache.getUserById(userInfoByKey.getUserId());
        }
        return null;
    }

    /**
     * 获取登录用户登录信息
     * @return
     */
    public static ThirdPartUserInfo getLoginUserInfo(){
        return getLoginUserInfo(getRequest());
    }

    public static ThirdPartUserInfo getLoginUserInfo(HttpServletRequest request){
        String token = getToken(request);
        if(token==null){
            return null;
        }
        Claims claims = JwtUtils.parser(token);
        String identifier = claims.getSubject();
        ThirdPartUserInfo userInfoByKey = UserCache.getUserInfoByKey(identifier);
        return userInfoByKey;
    }

    private static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getToken(HttpServletRequest req){
        String token = req.getHeader(JwtUtils.TOKEN_NAME);
        if(token==null || "".equals(token)){
            Cookie[] cookies = req.getCookies();
            if(cookies!=null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals(JwtUtils.TOKEN_NAME)){
                        token = cookie.getValue();
                    }
                }
            }
        }
        return token;
    }

}
