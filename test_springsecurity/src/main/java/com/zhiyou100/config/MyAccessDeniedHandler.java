package com.zhiyou100.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Map<String,Object> map = new HashMap<>();
        map.put("message",e.getMessage());
        map.put("stackTrace", e.getStackTrace());
        map.put("cause", e.getCause());
        response.getWriter().println("{\"code\":40000,\"message\":\"小弟弟，你没有权限访问呀！\",\"data\":\""+map+"\"}");
        response.getWriter().flush();
    }
}
