package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author syf_12138
 * @Description Zookeeper注册中心主启动类
 *              @EnableDiscoveryClient //该注解用于向使用consul或者zookeeper作为注册中心时注册服务
 * @create 2022/8/23 10:19
 */

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain8004 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8004.class, args);
    }
}
