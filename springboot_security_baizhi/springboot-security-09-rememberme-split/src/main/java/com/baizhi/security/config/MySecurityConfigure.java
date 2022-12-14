package com.baizhi.security.config;

import com.baizhi.security.filter.LoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author syf_12138
 * @Description 自定义资源权限规则
 * @create 2022/8/15 15:21
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

    /**
     * 作用: 用来将自定义AuthenticationManager在工厂中进行暴露,可以在任何位置注入
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        // 参数1：⾃定义⼀个⽣成令牌 key 默认 UUID
        // 参数2：认证数据源
        // 参数3：令牌存储
        return new MyPersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(), userDetailsService(), new InMemoryTokenRepositoryImpl());
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        // 指定认证 url
        loginFilter.setFilterProcessesUrl("/doLogin");
        // 设置用户名字段
        loginFilter.setUsernameParameter("username");
        // 设置密码字段
        loginFilter.setPasswordParameter("password");
        // 此时知道了前端输入过来的用户密码，就可以去数据库查询了
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        // 设置认证成功时使用自定义rememberMeService(即登录成功保存本地内存、数据库的数据)
        loginFilter.setRememberMeServices(rememberMeServices());
        // 认证成功处理
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("msg", "登录成功");
            result.put("用户信息", authentication.getPrincipal());
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().println(s);
        });
        // 认证失败处理
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("msg", "登录失败: " + exception.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().println(s);
        });

        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启请求权限管理
        http.authorizeRequests()
                // 所有请求在认证之后才能访问
                .anyRequest().authenticated()
                // 开启表单认证
                .and().formLogin()
                // 开启记住我功能
                .and().rememberMe()
                // 总是记住我功能
                // .alwaysRemember(true)
                // 设置自动登录使用哪个 rememberMeServices（再次登录时用于获取数据解码比对）
                .rememberMeServices(rememberMeServices())
                // 认证异常
                .and().exceptionHandling()
                // 异常处理
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().println("请认证之后再去处理!");
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

        // 将自定义用户登录过滤器替换过滤器链中的 filter（当用户请求开启表单认证formLogin()之后，原本利用UsernamePasswordAuthenticationFilter进行用户信息验证，现替换为自定义的过滤器进行验证）
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        // 将自定义用户登录过滤器放在过滤器链中那个 filter 之前
        // http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        // 将自定义用户登录过滤器放在过滤器链中那个 filter 之后
        // http.addFilterAfter(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
