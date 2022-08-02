package com.atguigu.springboot.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author syf_12138
 * @Description SpringApplicationRunListener调用流程
 * @create 2022/8/2 17:29
 */

public class HelloSpringApplicationRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;
    private final String[] args;

    // 必须有的构造器
    public HelloSpringApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    /**
     * 刚执行run方法时
     */
    @Override
    public void starting() {
        System.out.println("刚执行run方法时  HelloSpringApplicationRunListener的starting方法...");
    }

    /**
     * 环境建立好时候
     */
    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        Object o = environment.getSystemProperties().get("os.name");
        System.out.println("环境建立好时候  HelloSpringApplicationRunListener的environmentPrepared方法..." + o);
    }

    /**
     * 上下文建立好的时候
     */
    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("上下文建立好的时候  HelloSpringApplicationRunListener的contextPrepared方法...");
    }

    /**
     * 上下文载入配置时候
     */
    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("上下文载入配置时候  HelloSpringApplicationRunListener的contextLoaded方法...");
    }

    /**
     * 上下文刷新完成后，run方法执行完之前
     */
    @Override
    public void finished(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("上下文刷新完成后，run方法执行完之前  HelloSpringApplicationRunListener的failed方法...");
    }
}
