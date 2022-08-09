package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author syf_12138
 * @Description 受保护资源控制类
 * @Date 2022/8/7 19:27
 **/

@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello() {
        System.out.println("hello security!!!");
        return "hello security!!!";
    }

}
