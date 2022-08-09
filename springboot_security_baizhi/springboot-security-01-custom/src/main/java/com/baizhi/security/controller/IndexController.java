package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author syf_12138
 * @Dercription 公共资源控制类
 * @Date 2022/8/7 19:31
 **/

@RestController
public class IndexController {

    @RequestMapping("index")
    public String index() {
        System.out.println("hello index");
        return "hello index";
    }
}
