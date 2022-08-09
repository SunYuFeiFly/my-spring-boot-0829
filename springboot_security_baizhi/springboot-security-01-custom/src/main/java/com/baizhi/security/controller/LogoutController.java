package com.baizhi.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author syf_12138
 * @Description 测试注销登录控制类
 * @create 2022/8/9 16:30
 */

@Controller
public class LogoutController {

    @RequestMapping("/logout.html")
    public String logout() {
        System.out.println("开始跳转到用户注销页面");
        return "logout";
    }
}
