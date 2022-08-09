package com.baizhi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author syf_12138
 * @Description 利用配置类配置资源权限规则
 * @create 2022/8/9 14:15
 */

@EnableWebSecurity
@Configuration
public class MyWebSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 开启请求前权限管理
        return http.authorizeRequests()
            // 对于/index放行，无需认证和授权即可访问（方信息港资源写在anyRequest()之前）
            .antMatchers("/index").permitAll()
            .antMatchers("/login.html").permitAll()
            // 所有请求在认证之后才能访问
            .anyRequest().authenticated()
            .and()
            // 开启表单认证
            .formLogin()
            // 设置自己的登录页面(/login.html是请求，需通过控制器实现跳转到真正的登录页面，且要紧跟设置登录url)
            .loginPage("/login.html")
            // 指定处理请求登录的url
            .loginProcessingUrl("/doLogin")
            //设置登录页用户字段
            .usernameParameter("uname")
            // 设置登录页面密码字段
            .passwordParameter("pwd")
            // 认证成功跳转页面(服务器内部的 forewrd跳转，地址不变，始终会跳转/index请求)
            // .successForwardUrl("/index")
            // 默认认证成功跳转页面（重定向，路径发生改变,如果请求路径有返回请求页面，会优先跳转该路径，及、hello请求有返回内容，则优先返回该内容）(前后端不适用，返回json比较合适)
            // .defaultSuccessUrl("/index")
            // 默认认证成功跳转页面(总会跳转设定请求路径)(前后端不适用，返回json比较合适)
            // .defaultSuccessUrl("/index", true)
            // 请求认证成功处理（返回json数据，适合前后端分离）
            .successHandler(new MyAuthenticationSuccessHandler())
            // 认证失败 forward 跳转(前后端不适用，返回json比较合适)
            // .failureForwardUrl("/login.html")
            // 认证失败 redirect 重定向(前后端不适用，返回json比较合适)
            // .failureUrl("/login.html")
            // 请求认证失败处理（返回json数据，适合前后端分离）
            .failureHandler(new MyAuthenticationFailureHandler())
            // 禁止csrf跨站请求保护
            .and().csrf().disable()
            .build();
    }
}
