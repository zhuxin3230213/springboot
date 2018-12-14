package cn.gmuni.security.filter;

import cn.gmuni.security.utils.ResponseUtil;
import cn.gmuni.security.exception.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private long tokenLoseEfficacy = 7200000;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
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
        if (token == null || !token.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request, response);
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
        String loginId = request.getHeader("loginId");
        Claims claims = Jwts.parser().setSigningKey("MyJwtSecret")
                .parseClaimsJws(token.replace("Bearer ", ""))
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
                        .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                        .compact();
                UserCodeCache.USER_CODE_MAP.put(username + loginId, token);
                response.addHeader("token", "Bearer " + token);
            }
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        }
        return null;
    }


}