package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author syf_12138
 * @Description 测试控制类
 * @Date 2022/8/14 20:17
 **/

@RestController
public class DemoController {


    @RequestMapping("/demo")
    public String demo() {
        System.out.println("demo ok");
        return "demo ok!";
    }
}
