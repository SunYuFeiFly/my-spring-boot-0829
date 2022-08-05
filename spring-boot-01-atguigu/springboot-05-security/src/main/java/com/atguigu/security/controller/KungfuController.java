package com.atguigu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author syf_12138
 * @Description 配合权限认证操作类
 * @create 2022/8/5 16:12
 */

@Controller
public class KungfuController {

    private final String PREFIX = "pages/";

    /**
     * 欢迎页
     */
    @GetMapping("/")
    public String index() {
        System.out.println("访问首页！");
        return "welcome";
    }

    /**
     * 登陆页
     * @return
     */
    @GetMapping("/login")
    public String loginPage() {
        return PREFIX+"login";
    }

    /**
     * level1页面映射
     */
    @GetMapping("/level1/{path}")
    public String level1(@PathVariable("path")String path) {
        return PREFIX+"level1/"+path;
    }

    /**
     * level2页面映射
     */
    @GetMapping("/level2/{path}")
    public String level2(@PathVariable("path")String path) {
        return PREFIX+"level2/"+path;
    }

    /**
     * level3页面映射
     */
    @GetMapping("/level3/{path}")
    public String level3(@PathVariable("path")String path) {
        return PREFIX+"level3/"+path;
    }


}
