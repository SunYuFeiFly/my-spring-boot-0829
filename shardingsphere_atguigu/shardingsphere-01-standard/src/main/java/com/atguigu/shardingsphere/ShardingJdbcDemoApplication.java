package com.atguigu.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author syf_12138
 * @Description 水平拆分数据主启动类
 * @Date 2022/8/28 12:50
 **/

@MapperScan("com.atguigu.shardingsphere.mapper")
@SpringBootApplication
public class ShardingJdbcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDemoApplication.class, args);
    }
}
