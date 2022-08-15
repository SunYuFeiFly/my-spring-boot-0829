package com.baizhi.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * @author syf_12138
 * @Description 自定义资源权限规则
 * @create 2022/8/15 10:10
 */

@Configuration
public class MySecurityConfigure extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    public MySecurityConfigure(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("syf").password("{noop}123456").roles("admin").build());
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
     * 作用: 用来将自定义AuthenticationManager在工厂中进行暴露,可以在任何位置注入
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启前请求权限管理
        http.authorizeRequests()
                // 对于/index放行，无需认证和授权即可访问（方信息港资源写在anyRequest()之前）
                .antMatchers("/login.html").permitAll()
                // 所有请求在认证之后才能访问
                .anyRequest().authenticated()
                // 开启表单认证
                .and().formLogin()
                // 开启记住我功能
                .and().rememberMe()
                // 指定记住我自定义实现（基于内存）
                // .rememberMeServices(rememberMeServices())
                // 指定记住我自定义实现（数据库持久化）
                .tokenRepository(persistentTokenRepository())
                // 总是开启记住我功能，与前端登录页是否勾选无关
                // .alwaysRemember(true)
                // 禁止csrf跨站请求保护
                .and().csrf().disable();
    }


    /**
     * 指定记住我的实现（基于内存）
     */
    @Bean
    public RememberMeServices rememberMeServices() {
        // 参数1：⾃定义⼀个⽣成令牌 key 默认 UUID
        // 参数2：认证数据源
        // 参数3：令牌存储
        return new PersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(), userDetailsService(), new InMemoryTokenRepositoryImpl());
    }

    /**
     * 指定记住我的实现（数据库持久化）
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
         jdbcTokenRepository.setDataSource(dataSource);
        // 启动创建表结构（第二次启动设置为false，因为表已经存在）
         jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }
}
