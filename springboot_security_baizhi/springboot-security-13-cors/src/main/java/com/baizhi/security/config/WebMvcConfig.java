package com.baizhi.security.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author syf_12138
 * @Description 全局跨域配置
 * @create 2022/8/16 15:54
 */

// @Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 对那些请求进行跨域处理
        registry.addMapping("/**")
        // 允许请求凭证
        .allowCredentials(false)
        // 返回请求头
        .allowedHeaders("*")
        // 允许请求方法
        .allowedMethods("*")
        // 允许请求来源
        .allowedOrigins("*")
        .maxAge(3600);
    }
}
