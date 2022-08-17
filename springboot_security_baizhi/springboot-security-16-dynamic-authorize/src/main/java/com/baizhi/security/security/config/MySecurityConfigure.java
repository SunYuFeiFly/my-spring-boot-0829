package com.baizhi.security.security.config;


import com.baizhi.security.security.metasecourse.CustomerSecurityMetaSource;
import com.baizhi.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author syf_12138
 * @Description 自定义资源权限规则
 * @create 2022/8/17 15:18
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class MySecurityConfigure extends WebSecurityConfigurerAdapter {

    private final CustomerSecurityMetaSource customSecurityMetadataSource;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public MySecurityConfigure(CustomerSecurityMetaSource customSecurityMetadataSource, MyUserDetailsService userDetailsService) {
        this.customSecurityMetadataSource = customSecurityMetadataSource;
        this.userDetailsService = userDetailsService;
    }

    // @Bean
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
        auth.userDetailsService(userDetailsService);
    }


    /**
     * 所有配置全部针对前后端分离系统
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1.获取工厂对象
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        // 2.设置自定义 url 权限处理
        http.apply(new UrlAuthorizationConfigurer<>(applicationContext))
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(customSecurityMetadataSource);
                        // 是否拒绝公共资源访问(没有被权限保护的资源)
                        object.setRejectPublicInvocations(false);
                        return object;
                    }
                });

        // 开启请求权限管理
        http.authorizeRequests()
                // 所有请求在认证之后才能访问
                .anyRequest().authenticated()
                // 开启表单认证
                .and().formLogin()
                // 禁止csrf跨站请求保护
                .and().csrf().disable();
    }

}
