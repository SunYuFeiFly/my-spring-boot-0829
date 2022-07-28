package com;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 测试控制层位于主启动类上一层，不添加扫描路径，是否能访问成功
 * @create 2022/7/28 14:09
 */

@RestController
@RequestMapping("/tset")
public class TestHelloController {

    @RequestMapping("testHello")
    public String testHello() {
        return "test Hello!!!";
    }
}
