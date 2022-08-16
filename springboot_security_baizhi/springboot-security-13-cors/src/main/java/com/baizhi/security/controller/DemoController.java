package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 跨域测试控制类
 * @create 2022/8/16 15:40
 */

@RestController
public class DemoController {

    @GetMapping("/demo")
    // @CrossOrigin()
    public String demo() {
        System.out.println("demo ok!");
        return "demo ok!";
    }
}
