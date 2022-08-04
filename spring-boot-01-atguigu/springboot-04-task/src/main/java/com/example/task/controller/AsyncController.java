package com.example.task.controller;

import com.example.task.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author syf_12138
 * @Description 异步方法测试控制类
 * @create 2022/8/4 18:02
 */

@Controller
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/testAsync")
    public String testAsync() {
        asyncService.hello();
        return "success";
    }
}
