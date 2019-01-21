package cn.gmuni.sc.security.filter;

import cn.gmuni.sc.security.exception.TokenException;
import cn.gmuni.sc.security.token.BackgroundAuthenticationToken;
import cn.gmuni.sc.utils.JwtUtils;
import cn.gmuni.sc.utils.ResponseUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 后端登录token校验
 */
public class BackgroundTokenAuthenticationFilter extends BasicAuthenticationFilter {

    private long tokenLoseEfficacy = 7200000;

    public BackgroundTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String device = request.getHeader("Request-Device");
        if(null==device || "".equals(device)){
            Cookie[] cookies = request.getCookies();
           if(null!=cookies){
               for(Cookie cookie : cookies){
                   if ("Request-Device".equals(cookie.getName())){
                       device = cookie.getValue();
                       break;
                   }
               }
           }
        }
        if(!"app".equals(device)){
            AbstractAuthenticationToken abstractAuthenticationToken = null;
            try {
                abstractAuthenticationToken = getAuthentication(request,response);
                if(abstractAuthenticationToken==null){
                    chain.doFilter(request, response);
                    return;
                }
                SecurityContextHolder.getContext().setAuthentication(abstractAuthenticationToken);
                //放行
                chain.doFilter(request, response);
            } catch (TokenException e) {
                Map<String, Object> map = new HashMap<>();
                map.put("msg", e.getMessage());
                ResponseUtils.responseMsg(response,map,HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
            return;
        }
        chain.doFilter(request,response);
    }


    private AbstractAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws TokenException {
        String token = request.getHeader("token");
        if(token==null){
            return null;
        }
        String loginId = request.getHeader("loginId");
        Claims claims = JwtUtils.parser(token);
        //得到用户名
        String username = claims.getSubject();
        if (null == username || StringUtils.isEmpty(loginId)) {
            throw new TokenException("userNameNotExist");
        }
        //得到过期时间
        Date expiration = claims.getExpiration();

        //判断是否过期
        Date now = new Date();

        if (now.getTime() > expiration.getTime()) {
            throw new TokenException("timeOver");
        }
        //续期
        if(username!=null){
            if (expiration.getTime() - now.getTime() < tokenLoseEfficacy / 2) {
                response.addHeader("token",JwtUtils.generate(username,new Date(System.currentTimeMillis() + tokenLoseEfficacy)));
            }
        }
        AbstractAuthenticationToken authentication = new BackgroundAuthenticationToken(username,null,new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
