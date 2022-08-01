package com.atguigu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author syf_12138
 * @Description 简单controller功能测试
 *              1、所有 /webjars/** ，都去 classpath:/META-INF/resources/webjars/ 找资源；localhost:8080/webjars/jquery/3.3.1/jquery.js
 *              2、"/**" 访问当前项目的任何资源，都去（静态资源的文件夹）找映射
 *                  "classpath:/META‐INF/resources/"
 *                  "classpath:/resources/"
 *                  "classpath:/static/"
 *                  "classpath:/public/"
 *                  "/"：当前项目的根路径
 *               3、欢迎页； 静态资源文件夹下的所有index.html页面；被"/**"映射；
 *               4、所有的 /favicon.ico 都是在静态资源文件下找（图标）
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
