package com.example.task.controller;

import com.example.task.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 异步方法测试控制类
 * @create 2022/8/4 18:02
 */

@RestController
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    /**
     * asyncService调用的hello()方法是异步方法，所以当运行到hello方法后，并没有因此等hello()执行完再作打印任务，而是直接让hello()新建线程执行，同时打印内容。
     * @return
     */
    @GetMapping("/testAsync")
    public String testAsync() {
        asyncService.hello();
        return "success";
    }
}
