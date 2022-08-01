package com.atguigu.springboot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * @author syf_12138
 * @Description 自定义视图解析器
 * @create 2022/8/1 17:43
 */

@Configuration
public class ViewResolverConfig {

    @Bean
    public ViewResolver viewResolver() {
        return new MyViewResolver();
    }

    public static class MyViewResolver implements ViewResolver {
        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }
}
