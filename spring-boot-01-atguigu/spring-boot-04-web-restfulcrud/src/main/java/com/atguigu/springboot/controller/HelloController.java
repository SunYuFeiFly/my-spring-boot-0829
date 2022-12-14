package com.atguigu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * @author syf_12138
 * @Description 简单controller功能测试
 * 1、所有 /webjars/** ，都去 classpath:/META-INF/resources/webjars/ 找资源；localhost:8080/webjars/jquery/3.3.1/jquery.js
 * 2、"/**" 访问当前项目的任何资源，都去（静态资源的文件夹）找映射
 * "classpath:/META‐INF/resources/"
 * "classpath:/resources/"
 * "classpath:/static/"
 * "classpath:/public/"
 * "/"：当前项目的根路径
 * 3、欢迎页； 静态资源文件夹下的所有index.html页面；被"/**"映射；
 * 4、所有的 /favicon.ico 都是在静态资源文件下找（图标）
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

    @RequestMapping("testThymeleaf")
    public String testThymeleaf(Map<String, Object> map) {
        System.out.println("测试thymeleaf!!!");
        map.put("hello", "<h1>你好</h1>");
        map.put("users", Arrays.asList("zhangsan", "lisi", "wangwu"));
        // 会跳转到/templates/success.html文件
        return "success";
    }

    /**
     * 确保首页访问"loccalhost:8888/crud访问的位置是templates/index.html，而不是public/index.html"
     * 现在只是简单的跳转功能，可以不用单独写controller，可以利用springmvc拓展功能实现即可
     */
//    @RequestMapping({"/","/index.html"})
//    public String index(){
//        return "index";
//    }
}
