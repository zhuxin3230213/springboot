package cn.gmuni.sc.security.filter;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.sc.security.service.UserService;
import cn.gmuni.sc.security.token.BackgroundAuthenticationToken;
import cn.gmuni.sc.utils.JwtUtils;
import cn.gmuni.utils.IdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

public class BackgroundAuthenticationProcessingFilter  extends AbstractAuthenticationProcessingFilter {

    private AuthenticationManager authenticationManager;

    public BackgroundAuthenticationProcessingFilter(AuthenticationManager authenticationManager){
        super(new AntPathRequestMatcher("/login","POST"));
        this.authenticationManager = authenticationManager;
        this.setAuthenticationManager(authenticationManager);
    }

    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        Map m = getRequestPayload(httpServletRequest);
        String username = m.get("username").toString();
        String password = m.get("password").toString();
        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();
        BackgroundAuthenticationToken token = new BackgroundAuthenticationToken(username,password,new ArrayList<>());
        Map<String,Object> map = new HashMap<>();
        map.put("pwd",password);
        map.put("username",username);
        token.setDetails(map);
        return this.getAuthenticationManager().authenticate(token);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = JwtUtils.generate(authResult.getName(),new Date(System.currentTimeMillis()+1000*60*60*2));
        response.addHeader("token",token);
        String encoding = "utf-8";
        response.setContentType("text/plain;charset=" + encoding);
        response.setCharacterEncoding(encoding);
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("token", response.getHeader("token"));
        String loginId = IdGenerator.getId();
        //登录成功后将部分用户信息读取并返回到前台
        map.put("userInfo", userService().login(authResult.getName()));
        map.put("loginId", loginId);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(map);
        response.getWriter().write(s);
        response.getWriter().flush();
    }

    private UserService userService(){
        return (UserService) ApplicationContextProvider.getBean("loginUserService");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    private Map getRequestPayload(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        char[] buff = new char[1024];
        try (BufferedReader reader = req.getReader()) {
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
