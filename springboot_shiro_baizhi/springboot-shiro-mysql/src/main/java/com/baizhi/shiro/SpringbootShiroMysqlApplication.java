package com.baizhi.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages="com.baizhi.shiro.dao")
@SpringBootApplication
public class SpringbootShiroMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShiroMysqlApplication.class, args);
    }

}
