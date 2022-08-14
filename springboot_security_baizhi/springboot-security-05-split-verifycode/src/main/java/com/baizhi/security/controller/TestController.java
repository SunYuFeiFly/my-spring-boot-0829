package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author syf_12138
 * @Description 测试控制类
 * @Date 2022/8/14 16:48
 **/

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        System.out.println("test ...");
        return "test ok!";
    }
}
