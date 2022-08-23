package com.atguigu.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;

/**
 * @author syf_12138
 * @Description Feign配置（日志）
 * @create 2022/8/23 15:46
 */

@Configuration
public class MyFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
