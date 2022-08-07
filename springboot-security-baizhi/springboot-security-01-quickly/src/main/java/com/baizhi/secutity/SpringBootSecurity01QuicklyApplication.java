package com.baizhi.secutity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 默认引入security依赖便已经开始对所有请求起到拦截保护作用，且不用配置类进行配置，开启权限
 */

@SpringBootApplication
public class SpringBootSecurity01QuicklyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurity01QuicklyApplication.class, args);
    }

}
