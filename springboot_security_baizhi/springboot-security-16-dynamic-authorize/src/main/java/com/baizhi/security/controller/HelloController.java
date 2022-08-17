package com.baizhi.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 测试控制类
 * @create 2022/8/17 15:04
 */

@RestController
public class HelloController {

    /**
     *  访问权限路径       角色
     *   /admin/**     ROLE_ADMIN
     *   /user/**      ROLE_USER
     *   /guest/**     ROLE_USER ROLE_GUEST
     *
     *     用户           角色
     *    admin        ADMIN USER
     *    user         USER
     *    blr          GUEST
     */

    @GetMapping("/admin/hello")
    public String admin() {
        return "hello admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        return "hello user";
    }

    @GetMapping("/guest/hello")
    public String guest() {
        return "hello guest";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
