package com.baizhi.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author syf_12138
 * @Description 自定义资源权限规则
 * @create 2022/8/15 16:56
 */

@Configuration
public class MySecurityConfigure extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启请求权限管理
        http.authorizeRequests()
                // 所有请求在认证之后才能访问
                .anyRequest().authenticated()
                // 开启表单认证
                .and().formLogin()
                // 开启注销登录，拿到注销登录处理对象
                .and().logout()
                // 多个请求都能实现退出登录需求，且请求方式不固定为GET
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/logout", HttpMethod.GET.name()),
                        new AntPathRequestMatcher("/logout",HttpMethod.DELETE.name())))
                // 对否让session失效
                .invalidateHttpSession(true)
                // 清除认证标记
                .clearAuthentication(true)
                // 禁止csrf跨站请求保护
                .and().csrf().disable()
                // 开启会话管理
                .sessionManagement()
                // 允许最大会话并发数量
                .maximumSessions(1)
                // 当用户会话被挤下线值后的跳转路径(传统，后登录者挤掉前面登录人员)
                // .expiredUrl("/login");
                // 会话失效反馈（前后端分离架构处理分案）
                .expiredSessionStrategy(event -> {
                    HttpServletResponse response = event.getResponse();
                    Map<String, Object> result = new HashMap<>();
                    result.put("status", 500);
                    result.put("msg", "当前会话已经失效,请重新登录!");
                    String s = new ObjectMapper().writeValueAsString(result);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().println(s);
                    response.flushBuffer();
                })
                // 将session交给谁管理（redis）
                .sessionRegistry(sessionRegistry());
                // 一旦当前处于登录状态，其他设备无法登录，需等待当前设备账号退出
                // .maxSessionsPreventsLogin(true);
    }

    private final FindByIndexNameSessionRepository findByIndexNameSessionRepository;

    @Autowired
    public MySecurityConfigure(FindByIndexNameSessionRepository findByIndexNameSessionRepository) {
        this.findByIndexNameSessionRepository = findByIndexNameSessionRepository;
    }

    /**
     * 创建session同步到redis中方案
     */
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(findByIndexNameSessionRepository);
    }

    /**
     * 对session进行监听，用户登录向session Map中添加session，当用户退出则从Map中移除该session（当session交给redis管理，次数便不用监听了）
     */
    // @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
