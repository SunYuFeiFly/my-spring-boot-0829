package com.atguigu.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author syf_12138
 * @Description 指定启动端口的方式
 *      1、yml代码块配置
 *      2、properties多文件指定启动文件
 *      3、vim设置启动端口
 *      4、运行打包好的jar是设置server.port值
 * @create 2022/7/28 22:55
 */

@SpringBootApplication
public class SpringBoot02ProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot02ProfileApplication.class, args);
    }

}
