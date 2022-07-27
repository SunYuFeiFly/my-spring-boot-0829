package com.baizhi.shiro.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author syf_12138
 * @Description 获取UserService实例工具类
 * @create 2022/7/26 23:49
 */

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    public static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据 bean 名字，获取工厂中指定的 bean 对象
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
}
