package com.baizhi.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 测试jsp模板集成
 * @Author syf_12138
 * @Date 2022/7/21 14:59
 */

@Controller
@RequestMapping("jsp")
public class JspController {

    /**
     *测试路径：http://localhost:8888/springboot-inject/jsp/test
     */
    @RequestMapping("test")
    public String test(){
        System.out.println("jsp ok");
        return "index";
    }
}
