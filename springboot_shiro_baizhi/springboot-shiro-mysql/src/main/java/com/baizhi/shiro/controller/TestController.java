package com.baizhi.shiro.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 测试构建项目基本功能
 * @create 2022/7/26 11:18
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("hello")
    public String hello() {
        System.out.println("12138");
        return "springboot shiro hello !!!";
    }
}
