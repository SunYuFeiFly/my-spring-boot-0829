package com.baizhi.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 测试获取用户信息控制类
 * @create 2022/8/11 14:01
 */

@RestController
public class TestController {

    @RequestMapping("/user")
    public String user() {
        //1.代码中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //2.通过获取用户信息
        User user = (User) authentication.getPrincipal();
        System.out.println("username= " + user.getUsername());
        System.out.println("authorities= " + user.getAuthorities());
        return "user ok";
    }
}
