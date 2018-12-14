package cn.gmuni.sc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {
    public static void responseMsg(HttpServletResponse response, Object result, int status) throws IOException {
        String encoding = "utf-8";
        response.setContentType("text/plain;charset=" + encoding);
        response.setCharacterEncoding(encoding);
        response.setStatus(status);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(result);
        response.getWriter().write(s);
        response.getWriter().flush();
    }
}
