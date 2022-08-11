package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 简单控制类测试
 * @create 2022/8/11 15:26
 */

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        System.out.println("test ....");
        return "test ok!";
    }
}
