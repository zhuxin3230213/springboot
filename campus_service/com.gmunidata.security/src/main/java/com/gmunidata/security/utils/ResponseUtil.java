package com.gmunidata.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ResponseUtil {

    public static void writeMap(Map map, HttpServletResponse response){
        try {
            ObjectMapper mapper = new ObjectMapper();
            response.addHeader("tokenCheck", mapper.writeValueAsString(map));
            response.setStatus(response.SC_NON_AUTHORITATIVE_INFORMATION);
            response.getWriter().flush();
        } catch (Exception e1) {

        }

    }
}
