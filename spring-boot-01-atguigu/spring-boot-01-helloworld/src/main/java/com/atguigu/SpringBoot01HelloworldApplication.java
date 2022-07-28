package com.atguigu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 *  主启动类只能扫描@ComponentScan注入值，所以位于主启动类上一层的控制类需用@ComponentScan改变扫描陆金进行扫描范围扩大，不然相应请求接口不起作用
 */

// @ComponentScan(basePackages="com.atguigu")
@ComponentScan(basePackages="com")
@SpringBootConfiguration
@EnableAutoConfiguration
public class SpringBoot01HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot01HelloworldApplication.class, args);
    }

}
