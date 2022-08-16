package com.baizhi.security.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * @author syf_12138
 * @Description 自定义跨域filter（优先级比securityFilter低，不起作用）
 * @create 2022/8/16 15:58
 */

// @Configuration
public class MyWebMvcConfig {

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 返回请求头
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        // 允许请求方法
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        // 允许请求来源
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        registrationBean.setFilter(new CorsFilter(source));
        // 在所有内置filter之前执行
        registrationBean.setOrder(-1);
        return registrationBean;
    }
}
