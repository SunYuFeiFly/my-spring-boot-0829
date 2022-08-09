package com.baizhi.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author syf_12138
 * @Description 自定义注销登录返回返回数据
 * @create 2022/8/9 16:37
 */

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<String, Object>();
        // 打印登录成功信息
        result.put("msg", "注销成功，当前的注销的对消为：" + authentication);
        // 打印状态码
        result.put("status", 200);
        // 打印认证信息
        result.put("authentication", authentication);
        // 设置响应类型
        response.setContentType("application/json;charset=UTF-8");
        // json格式转字符串
        String s = new ObjectMapper().writeValueAsString(result);
        // 打印json格式数据
        response.getWriter().println(s);
    }
}
