package com.baizhi.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author syf_12138
 * @Description 登录控制器
 * @Date 2022/8/7 20:47
 **/

@Controller
public class LoginController {

    @RequestMapping("/login.html")
    public String login() {
        System.out.println("跳转登录页");
        //封装为login.html
        return "login";
    }
}
