package com.baizhi.security.config;

import com.baizhi.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

/**
 * @Author syf_12138
 * @Description 自定义资源权限规则(此方法已过时)
 * @Date 2022/8/7 19:55
 **/

@EnableWebSecurity
@Configuration
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public MyWebSecurityConfigurer(UserService userService) {
        this.userService = userService;
    }

    /**
     * 在工厂中自定义userDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        // 获取用户名
        String username = "zhangsan";
        // 根据用户名获取用户信息
        com.baizhi.security.entity.User user = userService.loadUserByUsername(username);
        // noop表示明文
        userDetailsService.createUser(User.withUsername(user.getUsername()).password(user.getPassword()).roles(user.getAuthorities().toString()).build());
        return userDetailsService;
    }


    /**
     * 系统默认认证规则（此配置可不需要，当我们自定义了UserDetailsService，当我们没有自定义AuthenticationManagerBuilder时，默认就会把此UserDetailsService注入到AuthenticationManagerBuilder）
     */
    // @Autowired
    public void initialize(AuthenticationManagerBuilder builder) throws Exception {
        System.out.println("springboot默认配置:" + builder);
        // 系统扫描到UserDetailsService会自动将值设置给builder，并不用显式进行赋值(这里显式注入反而会造成循环调用)
        // builder.userDetailsService(userDetailsService());
    }


    /**
     * 自定义认证规则（推荐，当系统默认和自定义都存在时，默认自定义会替代默认,是工厂中的实现，不可以在其它地方注入使用）
     */
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        System.out.println("自定义AuthenticationManager:" + builder);
        builder.userDetailsService(userDetailsService());
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
            // .logoutSuccessUrl("/login.html")
            // 注销成功，返回自定义json内容（适合前后端分离系统）
            .logoutSuccessHandler(new MyLogoutSuccessHandler())
            // 禁止csrf跨站请求保护
            .and().csrf().disable();
    }
}
