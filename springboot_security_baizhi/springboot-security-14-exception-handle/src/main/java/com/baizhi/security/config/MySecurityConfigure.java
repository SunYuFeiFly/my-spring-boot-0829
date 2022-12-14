package com.baizhi.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author syf_12138
 * @Description 自定义资源权限规则
 * @create 2022/8/16 17:30
 */

@Configuration
public class MySecurityConfigure extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService UserDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("syf").password("{noop}123456").roles("admin").build());
        return inMemoryUserDetailsManager;
    }

    /**
     * 自定义认证规则（推荐，当系统默认和自定义都存在时，默认自定义会替代默认,是工厂中的实现，不可以在其它地方注入使用）
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(UserDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启请求权限管理
        http.authorizeRequests()
            // 所有请求在认证之后才能访问
            .anyRequest().authenticated()
            // 开启表单认证
            .and().formLogin()

            // 认证异常
            .and().exceptionHandling()
            // 异常处理
            .authenticationEntryPoint((request, response, exception) -> {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("尚未认证，请进行认证操作！");
            })
            .accessDeniedHandler((request, response, exception) -> {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("无权访问!");
            })

            // 开启注销登录，拿到注销登录处理对象
            .and().logout()
            // 指定注销登录的url(默认/logout，GET方式)
            // .logoutUrl("/logout")
            // 多个请求都能实现退出登录需求，且请求方式不固定为GET
            .logoutRequestMatcher(new OrRequestMatcher(
                    new AntPathRequestMatcher("/logout", HttpMethod.GET.name()),
                    new AntPathRequestMatcher("/logout",HttpMethod.DELETE.name())))
            // 对否让session失效
            .invalidateHttpSession(true)
            // 清除认证标记
            .clearAuthentication(true)
            // 注销成功跳转的请求(适用传统web开发)
            // .logoutSuccessUrl("/login.html")
            // 注销成功，返回自定义json内容（适合前后端分离系统）
            .logoutSuccessHandler((request, response, authentication) -> {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("msg", "注销成功");
                result.put("用户信息", authentication.getPrincipal());
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.OK.value());
                String s = new ObjectMapper().writeValueAsString(result);
                response.getWriter().println(s);
            })
            // 禁止csrf跨站请求保护
            .and().csrf().disable();
    }
}
