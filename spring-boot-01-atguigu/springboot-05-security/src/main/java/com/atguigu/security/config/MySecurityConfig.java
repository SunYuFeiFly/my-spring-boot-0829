package com.atguigu.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author syf_12138
 * @Description 自定义Security配置类，配置认证授权
 * @create 2022/8/5 17:17
 */

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 定义认证规则
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password("123456").roles("VIP1","VIP2","VIP3");
    }


    /**
     * 定义授权规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定制请求的授权规则
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        // 开启自动配置的登陆功能，效果，如果没有登陆，没有权限就会来到登陆页面
        // 如果没有登陆，没有权限就会来到默认生成的登陆页面
        http.formLogin();
        // 可以设置我们的页面作为登录页
        // http.formLogin().usernameParameter("user").passwordParameter("pwd").loginPage("/login");
        // 开启自动配置的注销功能(访问 /logout 表示用户注销，清空session)。
        // 不设置注销后跳转页面，默认注销成功会跳转 /login?logout 页面
         http.logout();
        // 注销成功以后来到首页
//        http.logout().logoutSuccessUrl("/");
    }
}
