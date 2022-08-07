package com.baizi.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author syf_12138
 * @Description  自定义登录成功处理：有时候页面跳转并不能满足我们，特别是在前后端分离开发中就不需要成功之后跳转页面，只需要给前端返回一个 JSON数据通知登录成功还是失败与否
 * @Date 2022/8/7 21:35
 **/

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    }
}
