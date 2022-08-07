package com.baizhi.secutity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author syf_12138
 * @Description 简单控制类（此时没有引入security，可以随意访问）
 * @Date 2022/8/7 15:12
 **/

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("Hello Spring Security");
        return "hello spring security";
    }
}
