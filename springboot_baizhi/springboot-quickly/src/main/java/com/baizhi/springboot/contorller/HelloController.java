package com.baizhi.springboot.contorller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description TODO
 * @create 2022/7/21 11:26
 */

@RestController
@RequestMapping("hello")
public class HelloController {

    /**
     * @Description 测试控制器
     *      访问地址：http://localhost:端口号/项目名/请求路径
     *      注意：springboot项目默认启动没有项目名，例如：http://localhost:8888/hello/test
     *      需要指定项目名时，要在application.yml里面进行配置
     */
    @RequestMapping("test")
    public String test() {
        System.out.println("hello springboot!!!");
        return "hello springboot!!!";
    }
}

/**
    # 注意: springboot的项目启动默认无项目名，此处指定了项目名
    - 访问路径: http://localhost:8888/springboot-quickly/hello/test
    ​
    # 注意: springboot的项目启动默认无项目名
    - 访问路径: http://localhost:8888/hello/test
 */