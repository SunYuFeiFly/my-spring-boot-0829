package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author syf_12138
 * @Description 测试控制类
 * @create 2022/8/15 10:03
 */

@RestController
public class IndexController {

    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        System.out.println("index  ok!");
        return "index ok!";
    }
}
