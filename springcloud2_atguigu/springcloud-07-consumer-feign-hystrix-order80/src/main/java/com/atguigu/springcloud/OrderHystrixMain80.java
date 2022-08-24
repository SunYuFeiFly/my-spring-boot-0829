package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author syf_12138
 * @Description Hystrix消费者主启动类
 *              异常处理流程：
 *              消费端方法未设置超时时间<1>
 *                  -- 生产端本身代码无异常
 *                      -- 生产端超时、异常 -> 获取生产端异常返回结果
 *                      -- 生产端正常 -> 正常处理结果
 *                  -- 生产端本身代码有异常
 *                      -- 有指定、全局fallback方法 -> 执行此方法
 *                      -- 没有指定、全局fallback方法
 *                          -- 有统一异常处理类（实现了远程调用service调用接口方法） -> 执行对应接口请求的实现方法
 *                          -- 没有统一异常处理类  -> 抛出异常
 *              消费端方法设置超时时间
 *                  -- 生产端执行时间 < 设置超时时间 -- 执行流程如上<1>
 *                  -- 生产端执行时间 > 设置超时时间
 *                      -- 有指定、全局fallback方法 -> 执行此方法
 *                      -- 没有指定、全局fallback方法
 *                          -- 有统一异常处理类（实现了远程调用service调用接口方法） -> 执行对应接口请求的实现方法
 *                          -- 没有统一异常处理类  -> 抛出异常
 * @create 2022/8/23 17:32
 */

@SpringBootApplication
@EnableFeignClients
@EnableHystrix
@EnableCircuitBreaker
public class OrderHystrixMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain80.class, args);
    }
}
