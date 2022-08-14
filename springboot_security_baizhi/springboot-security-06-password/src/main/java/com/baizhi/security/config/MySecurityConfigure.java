package com.baizhi.security.config;

import com.baizhi.security.service.MyUserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

/**
 * @Author syf_12138
 * @Description 自定义资源权限规则
 * @Date 2022/8/14 20:31
 **/

public class MySecurityConfigure extends WebSecurityConfigurerAdapter {

    /**
     * 此种方式固定了加密方式，此时密码直接就是加密后密码，不用在前面加{bcrypt}类似加密类型标志字符
     * @return
     */
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 第二种加密方式，查出的密码中带有加密方式，即{bcrypt}类似，这样美吗支持各种累行的加密
     */
    private MyUserDetailsService myUserDetailsService;

    public MySecurityConfigure(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    /**
     * 此处按理说应查询数据库实时数据，现直接设值测试
     */
    // @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("syf").password("{noop}123").roles("admin").build());
        return inMemoryUserDetailsManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(userDetailsService());
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启前请求权限管理
        http.authorizeRequests()
                // 对于/index放行，无需认证和授权即可访问（方信息港资源写在anyRequest()之前）
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
                .defaultSuccessUrl("/index.html")
                // 默认认证成功跳转页面(总会跳转设定请求路径)(前后端不适用，返回json比较合适)
                // .defaultSuccessUrl("/index", true)
                // 请求认证成功处理（返回json数据，适合前后端分离）
                // .successHandler(new MyAuthenticationSuccessHandler())
                // 认证失败 forward 跳转(前后端不适用，返回json比较合适)
                // .failureForwardUrl("/login.html")
                // 认证失败 redirect 重定向(前后端不适用，返回json比较合适)
                .failureUrl("/login.html")
                // 请求认证失败处理（返回json数据，适合前后端分离）
                // .failureHandler(new MyAuthenticationFailureHandler())
                // 开启注销登录，拿到注销登录处理对象
                .and().logout()
                // 指定注销登录的url(默认/logout，GET方式)
                // .logoutUrl("/logout")
                // 多个请求都能实现退出登录需求，且请求方式不固定为GET
                .logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/aa","GET"),
                        new AntPathRequestMatcher("/bb","POST"),
                        new AntPathRequestMatcher("/logout","GET")))
                // 对否让session失效
                .invalidateHttpSession(true)
                // 清除认证标记
                .clearAuthentication(true)
                // 注销成功跳转的请求(适用传统web开发)
                .logoutSuccessUrl("/login.html")
                // 注销成功，返回自定义json内容（适合前后端分离系统）
                // .logoutSuccessHandler(new MyLogoutSuccessHandler())
                // 禁止csrf跨站请求保护
                .and().csrf().disable();
    }
}
