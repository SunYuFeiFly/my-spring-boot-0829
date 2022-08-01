package com.atguigu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author syf_12138
 * @Description 简单controller功能测试
 * @create 2022/8/1 16:00
 */

@Controller
public class HelloController {

    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        System.out.println("hello springboot!!!");
        return "hello springboot!!!";
    }
}
