package com.baizi.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author syf_12138
 * @Description 自定义资源权限规则
 * @Date 2022/8/7 19:55
 **/

// @Configuration
public class MyWebSecurityConfigurer extends  WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启前请求权限管理
        http.authorizeHttpRequests()
                // 匹配请求
                // 对于/index放行，无需认证和授权即可访问（方信息港资源写在anyRequest()之前）
                .mvcMatchers("/index").permitAll()
                // 所有请求在认证之后才能访问
                .anyRequest().authenticated()
                .and()
                // 开启表单认证
                .formLogin()
                // 设置自己的登录页面
                .loginPage("/login");
    }
}
