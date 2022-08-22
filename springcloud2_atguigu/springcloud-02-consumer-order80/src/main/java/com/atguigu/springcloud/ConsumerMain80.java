package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author syf_12138
 * @Description 消费者主启动类
 * @create 2022/8/22 14:05
 */

@EnableEurekaClient
@SpringBootApplication
public class ConsumerMain80 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain80.class, args);
    }
}
