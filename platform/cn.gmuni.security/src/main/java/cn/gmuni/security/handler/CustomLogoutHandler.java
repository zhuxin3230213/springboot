package cn.gmuni.security.handler;

import cn.gmuni.security.filter.UserCodeCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandler  implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication) {
        String token = request.getHeader("token");
        String loginId = request.getHeader("loginId");
        Claims claims = Jwts.parser().setSigningKey("MyJwtSecret")
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        //得到用户名
        String username = claims.getSubject();
        if(username!=null){
            UserCodeCache.USER_CODE_MAP.remove(username+loginId);
            httpServletResponse.setHeader("token",null);
            httpServletResponse.setHeader("loginId",null);
        }
    }
}
