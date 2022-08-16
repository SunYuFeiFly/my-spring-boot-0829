package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 简单测试类
 * @create 2022/8/16 11:05
 */

@RestController
public class HelloController {

    @PostMapping("/hello")
    public String hello() {
        System.out.println("hello");
        return "hello";
    }
}
