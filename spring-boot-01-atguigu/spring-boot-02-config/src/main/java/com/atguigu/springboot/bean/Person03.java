package com.atguigu.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author syf_12138
 * @Description @PropertySource指定特定配置文件 + @ConfigurationProperties完成对象属性值注入
 * @Date 2022/7/28 22:10
 **/

@PropertySource(value="classpath:person.yml")
@ConfigurationProperties(prefix="person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class Person03 {

    private String lastName;
    private int age;
    private boolean boss;
    private Date birth;
    private Map<String, Object> maps;
    private List<String> lists;
    private Dog dog;
}