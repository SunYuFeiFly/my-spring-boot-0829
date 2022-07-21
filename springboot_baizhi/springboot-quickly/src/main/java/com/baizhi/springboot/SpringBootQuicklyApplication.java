package com.baizhi.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author syf_12138
 * @Description 应用入口类
 * @create 2022/7/21 11:19
 */

/**
 * @SpringBootApplication注解
 * 修饰范围：只能用在入口类上，且只能出现一次
 * 作用：标识这个类是一个springboot的入口类，是启动整个springboot项目的总入口
 * springboot项目创建步骤总结：
 *    1.pom.xml文件引入依赖
 *    2.resources目录下生成application.yml
 *    3.创建入口类加入@SpringBootApplication注解，在main中启动应用
 */
@SpringBootApplication
public class SpringBootQuicklyApplication {

    public static void main(String[] args) {
        /**
         * 启动springboot应用
         * 参数1：指定入口类的类对象(.class)，注意不是类的对象
         * 参数2：main函数参数
         * 注意：默认端口是8080，如果端口被占用，需要在application.yml中重新指定端口，否则不能正常启动项目
         */
        SpringApplication.run(SpringBootQuicklyApplication.class,args);
    }
}

/**
 *  @SpringBootApplication:  注解
 *      组合注解: 就是由多个注解组合而成一个注解
 *      元注解 : 用来修饰注解的注解，如：@Target、@Retention、@Documented、@Inherited
 *              @Target: 指定注解作用范围
 *              @Retention: 指定注解什么时候有效
 *   包含下面三个注解：
 *   @SpringBootConfiguration:
 *      这个注解就是用来自动配置spring、springmvc(初始化servlet ...)相关环境
 *
 *   @EnableAutoConfiguration: 开启自动配置
 *      自动配置核心注解  自动配置spring相关环境  自动与项目中引入的第三方技术自动配置其环境
 *      mybatis-springboot、redis-springboot 、es-springboot 、rabbitmq 第三方技术
 *
 *   @ComponentScan: 组件扫描
 *      根据注解发挥注解作用，默认扫描当前包及其子包
 *
 *  启动springboot应用时候需要传递main函数参数作为启动的第二个参数:
 *  主要用途是测试用，项目启动后动态传参，传递JVM相关的一些参数
 *
 *  外部部署时打包成jar包：java -jar --spring.config.location=绝对路径  xxx.jar
 */