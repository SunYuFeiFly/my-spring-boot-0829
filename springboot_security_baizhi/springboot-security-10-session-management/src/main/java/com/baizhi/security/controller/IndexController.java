package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 测试控制类
 * @create 2022/8/15 16:45
 */

@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        System.out.println("index ok!");
        return "index ok";
    }
}
