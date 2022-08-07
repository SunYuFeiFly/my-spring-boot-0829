package com.baizi.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author syf_12138
 * @Description 自定义登录成功处理：有时候页面跳转并不能满足我们，特别是在前后端分离开发中就不需要成功之后跳转页面，只需要给前端返回一个 JSON数据通知登录成功还是失败与否
 * @Date 2022/8/7 21:35
 **/

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<String, Object>();
        // 打印登录成功信息
        result.put("msg", "登录成功");
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
