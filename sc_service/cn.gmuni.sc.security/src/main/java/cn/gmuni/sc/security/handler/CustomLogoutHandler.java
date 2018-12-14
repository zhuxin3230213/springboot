package cn.gmuni.sc.security.handler;

import cn.gmuni.sc.log.constant.LoginLogType;
import cn.gmuni.sc.log.constant.LoginMethod;
import cn.gmuni.sc.log.constant.LoginOperator;
import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.utils.LoginLogger;
import cn.gmuni.sc.security.cache.LoginUserCache;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.service.IThirdUserService;
import cn.gmuni.sc.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandler  implements LogoutHandler {
    @Autowired
    IThirdUserService thirdUserServiceImpl;


    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String token = httpServletRequest.getHeader(JwtUtils.TOKEN_NAME);
        if ("app".equals(httpServletRequest.getHeader("Request-Device"))){
            LoginLog log = new LoginLog();
            try{
                ThirdPartUserInfo info = LoginUserCache.get(token);
                if(thirdUserServiceImpl.logout(info)){
                    LoginUserCache.logout(token);
                }
                log.setOperator(LoginOperator.LOGOUT.getCode());
                log.setLoginType(LoginMethod.PHONE.getCode());
                if(info!=null){
                    log.setUsername(info.getIndentifier());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            log.setLogDesc("用户退出登录");
            log.setLogType(LoginLogType.LOGIN_LOG.getCode());
            LoginLogger.log(log,httpServletRequest);
        }else {
             token = httpServletRequest.getHeader("token");
             Claims claims = JwtUtils.parser(token);
            //得到用户名
            String username = claims.getSubject();
            if(username!=null){
                httpServletResponse.setHeader("token",null);
                httpServletResponse.setHeader("loginId",null);
            }
        }

    }

}
