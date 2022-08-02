package com.atguigu.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author syf_12138
 * @Description 扩展SpringMVC的功能，既保留springboot的配置航能，也能实现我们的映射需求
 *              使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
 *              @EnableWebMvc 不要接管SpringMVC
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
}
