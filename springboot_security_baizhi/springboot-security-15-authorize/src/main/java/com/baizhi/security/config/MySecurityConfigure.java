package com.baizhi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author syf_12138
 * @Description 自定义资源权限规则
 * @create 2022/8/17 10:20
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class MySecurityConfigure extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("syf").password("{noop}123456").roles("ADMIN").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{noop}123").roles("ADMIN","USER").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("lisi").password("{noop}123").roles("USER").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("win7").password("{noop}123").authorities("READ_INFO").build());
        return inMemoryUserDetailsManager;
    }

    /**
     * 自定义认证规则（推荐，当系统默认和自定义都存在时，默认自定义会替代默认,是工厂中的实现，不可以在其它地方注入使用）
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    /**
     * 所有配置全部针对前后端分离系统
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启请求权限管理
        http.authorizeRequests()
                // 具有ADMIN角色  通用拦截url:/admin、/admin/、/admin.html
                .mvcMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN")
                // 具有USER角色  通用拦截url:/user、/user/、/user.html
                .mvcMatchers("/user").hasRole("USER")
                // 具有READ_INFO权限  通用拦截url:/getInfo、/getInfo/、/getInfo.html
                .mvcMatchers("/getInfo").hasAuthority("READ_INFO")
                // 所有请求在认证之后才能访问
                .anyRequest().authenticated()
                // 开启表单认证
                .and().formLogin()
                // 指定处理请求登录的url
                .loginProcessingUrl("/doLogin")
                //设置登录页用户字段
                .usernameParameter("username")
                // 设置登录页面密码字段
                .passwordParameter("password")
                // 禁止csrf跨站请求保护
                .and().csrf().disable();
    }

}
