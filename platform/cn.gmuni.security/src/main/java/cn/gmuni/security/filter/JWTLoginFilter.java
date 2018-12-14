package cn.gmuni.security.filter;


import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.org.model.UserInfo;
import cn.gmuni.security.service.UserService;
import cn.gmuni.utils.IdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
    private long tokenLoseEfficacy = 7200000;
    private AuthenticationManager authenticationManager;

    private UserService userService;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Map m = getRequestPayload(request);
        String username = m.get("username").toString();
            String password = m.get("password").toString();
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            Authentication auth = null;
            try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
        } catch (Exception e) {
            throw e;
        }
        return auth;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                //有效期两小时
                .setExpiration(new Date(System.currentTimeMillis() + tokenLoseEfficacy))
                //采用什么算法是可以自己选择的，不一定非要采用HS512
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();
        response.addHeader("token", "Bearer " + token);
        String encoding = "utf-8";
        response.setContentType("text/plain;charset=" + encoding);
        response.setCharacterEncoding(encoding);
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("token", response.getHeader("token"));
        String loginId = IdGenerator.getId();
        UserCodeCache.USER_CODE_MAP.put(authResult.getName() + loginId, token);
        //登录成功后将部分用户信息读取并返回到前台
        map.put("userInfo", getUserInfo(authResult.getName()));
        map.put("loginId", loginId);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(map);
        response.getWriter().write(s);
        response.getWriter().flush();
    }

    private UserInfo getUserInfo(String username){
        if(userService==null){
            userService = (UserService) ApplicationContextProvider.getBean("userService");
        }
        UserInfo info = userService.loadUserByUsername(username);
        UserInfo result = new UserInfo();
        BeanUtils.copyProperties(info,result,"password","createTime");
        return result;
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", failed.getMessage());
        map.put("success",false);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(map));
        response.getWriter().flush();
    }

    private Map getRequestPayload(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(sb.toString().getBytes(), Map.class);
            return map;
        } catch (Exception e) {

        }
        return null;
    }
}
