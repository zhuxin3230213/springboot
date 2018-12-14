package com.gmunidata.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Component
public class CustomHttpStatusReturningLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String encoding = "UTF-8";
        response.setContentType("text/plain;charset=" + encoding);
        response.setCharacterEncoding(encoding);
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(map);
        response.getWriter().write(s);
        response.getWriter().flush();
    }
}
