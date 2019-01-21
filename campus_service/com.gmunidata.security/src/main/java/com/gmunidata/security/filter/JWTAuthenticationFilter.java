package com.gmunidata.security.filter;

import com.gmunidata.security.SecurityConst;
import com.gmunidata.security.exception.TokenException;
import com.gmunidata.security.utils.ResponseUtil;
import com.gmunidata.utils.CustomApplicationContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private long tokenLoseEfficacy = 7200000;

    @Value("${anonymous.ip}")
    private String anonymousIp;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        anonymousIp = CustomApplicationContext.getProp("anonymous.ip");
    }


    /**
     * 在此方法中检验客户端请求头中的token,
     * 如果存在并合法,就把token中的信息封装到 Authentication 类型的对象中,
     * 最后使用  SecurityContextHolder.getContext().setAuthentication(authentication); 改变或删除当前已经验证的 pricipal
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader("token");

        //判断是否有token
        if ((token == null || !token.startsWith(SecurityConst.JWT_PREFIX)) && !"app".equals(request.getHeader("requestType"))) {
            chain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request, response);
            if(authenticationToken==null){
                //放行
                chain.doFilter(request, response);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //放行
            chain.doFilter(request, response);
        } catch (TokenException | ExpiredJwtException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("msg", e.getMessage());
            ResponseUtil.writeMap(map, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }


    }

    /**
     * 解析token中的信息,并判断是否过期
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws TokenException {
        String token = request.getHeader("token");
        String requestType = request.getHeader("requestType");
        //app端访问逻辑
        if(null != requestType && "app".equals(requestType)){
            return appTokenLogin(request, response);
        }
        String loginId = request.getHeader("loginId");
        Claims claims = Jwts.parser().setSigningKey(SecurityConst.JWT_KEY)
                .parseClaimsJws(token.replace(SecurityConst.JWT_PREFIX, ""))
                .getBody();
        //得到用户名
        String username = claims.getSubject();
        if (null == username || StringUtils.isEmpty(loginId) || !UserCodeCache.USER_CODE_MAP.containsKey(username + loginId)) {
            throw new TokenException("userNameNotExist");
        }
        //得到过期时间
        Date expiration = claims.getExpiration();

        //判断是否过期
        Date now = new Date();

        if (now.getTime() > expiration.getTime()) {
            UserCodeCache.USER_CODE_MAP.remove(username + loginId);
            throw new TokenException("timeOver");
        }


        if (username != null) {
            //重新计算token，相当于续期
            if (expiration.getTime() - now.getTime() < tokenLoseEfficacy / 2) {
                token = Jwts.builder()
                        .setSubject(username)
                        //有效期两小时
                        .setExpiration(new Date(System.currentTimeMillis() + tokenLoseEfficacy))
                        //采用什么算法是可以自己选择的，不一定非要采用HS512
                        .signWith(SignatureAlgorithm.HS512, SecurityConst.JWT_KEY)
                        .compact();
                UserCodeCache.USER_CODE_MAP.put(username + loginId, token);
                response.addHeader("token", SecurityConst.JWT_PREFIX + token);
            }
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        }
        return null;
    }

    /**
     * 校验app端登陆信息
     * @param request
     * @param response
     * @return
     * @throws TokenException
     */
    private UsernamePasswordAuthenticationToken appTokenLogin(HttpServletRequest request, HttpServletResponse response) throws TokenException{
        if(getIpAddress(request).equals(anonymousIp) && checkUrl(request)){
            return new UsernamePasswordAuthenticationToken("anonymous", null, new ArrayList<>());
        }
        String token = request.getHeader("token");
        if (token == null || !token.startsWith(SecurityConst.JWT_PREFIX)) {
            return null;
        }
        String loginId = request.getHeader("loginId");
        Claims claims = Jwts.parser().setSigningKey(SecurityConst.JWT_KEY)
                .parseClaimsJws(token.replace(SecurityConst.JWT_PREFIX, ""))
                .getBody();
        //得到用户名
        String username = claims.getSubject();
        if (null == username || StringUtils.isEmpty(loginId) || !UserCodeCache.USER_CODE_MAP.containsKey(username + loginId)) {
            throw new TokenException("userNameNotExist");
        }
        long currentMiles = new Date().getTime();
        String requestTime = request.getHeader("requestTime");
        long peroid = 3 * 60 * 1000;
        if (currentMiles < Long.parseLong(requestTime + peroid)){
            throw new TokenException("requestTimeOut");
        }
        if(username != null){
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        }
        return null;
    }


    /**
     * 校验url是否符合ip过滤的规则
     * @param request
     * @return
     */
    private boolean checkUrl(HttpServletRequest request){
        String servletPath = request.getServletPath();
        String paths = CustomApplicationContext.getProp("anonymous.paths");
        List<String> pathsList =Arrays.asList(paths.split(","));
        if(pathsList.indexOf(servletPath) > -1){
            return true;
        }
        if(pathsList.indexOf("/**") > -1){
            return true;
        }
        return false;
    }

    /**
     * 获取用户ip
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}