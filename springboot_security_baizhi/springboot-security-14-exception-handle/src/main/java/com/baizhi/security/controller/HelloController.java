package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 测试操作类
 * @create 2022/8/16 17:29
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        System.out.println("hello ok");
        return "hello ok!";
    }
}
