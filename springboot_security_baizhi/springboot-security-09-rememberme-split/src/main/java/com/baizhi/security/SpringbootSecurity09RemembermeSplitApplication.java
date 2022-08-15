package com.baizhi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 前后端分离-记住我功能使用步骤（springboot-security-03-split用户名、密码验证基础上）
 * 1、在自定义的LoginFilter.attemptAuthentication(request,response)方法中从request.getInputStream()获取map数据中同时获取"remember-me"参数的值放入request中
 * 2、重写PersistentTokenBasedRememberMeServices.rememberMeRequested()方法，判断是否是remember请求时，从request中获取remember-me参数值，比对确认
 * 3、自定义资源权限规则MySecurityConfigure两处增加设置
 *     1）、注入过滤器LoginFilter中loginFilter.setRememberMeServices(rememberMeServices())：当用户登录验证成功用什么样的规则保存数据至本地内存获取数据库
 *     2）、重写configure(HttpSecurity http)方法中
 *          // 开启记住我功能
 *          .and().rememberMe()
 *          // 设置自动登录使用哪个 rememberMeServices（再次登录时用于获取数据解码比对）
 *          .rememberMeServices(rememberMeServices())
 *
 *          @Bean
 *          public RememberMeServices rememberMeServices() {
 *              // 参数1：⾃定义⼀个⽣成令牌 key 默认 UUID
 *              // 参数2：认证数据源
 *              // 参数3：令牌存储
 *              return new MyPersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(), userDetailsService(), new InMemoryTokenRepositoryImpl());
 *          }
 */

@SpringBootApplication
public class SpringbootSecurity09RemembermeSplitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurity09RemembermeSplitApplication.class, args);
    }

}
