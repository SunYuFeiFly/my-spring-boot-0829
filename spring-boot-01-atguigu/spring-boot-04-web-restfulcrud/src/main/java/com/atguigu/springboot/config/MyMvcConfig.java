package com.atguigu.springboot.config;

import com.atguigu.springboot.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author syf_12138
 * @Description 扩展SpringMVC的功能，既保留springboot的配置航能，也能实现我们的映射需求
 * 使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
 * @EnableWebMvc 不要接管SpringMVC, 原因是其导入WebMvcConfigurationSupport类，导致自动配置类条件判断为false，将不加载自动配置功能
 * @create 2022/8/2 10:06
 */

@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 简单的页面跳转，不涉及数据则直接映射
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("atguigu").setViewName("success");
    }


    /**
     * 所有的WebMvcConfigurerAdapter组件都会一起起作用
     */
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            /**
             * 注册拦截器
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                super.addInterceptors(registry);
                LoginHandlerInterceptor interceptor = new LoginHandlerInterceptor();
                // SpringBoot已经做好了静态资源映射
                registry.addInterceptor(interceptor).addPathPatterns("/**")
                        .excludePathPatterns("/index.html", "/", "/user/login");
            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                super.addViewControllers(registry);
                // registry.addViewController("/").setViewName("index");
                // registry.addViewController("/index.html").setViewName("index");
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");


            }
        };

        return adapter;
    }


}
