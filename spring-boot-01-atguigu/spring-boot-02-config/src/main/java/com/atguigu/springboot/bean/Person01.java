package com.atguigu.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author syf_12138
 * @Description @ConfigurationProperties测试配置文件值注入时需将该实体类注册为bean，所以需标注@Component
 *              该实体类和实体类内部引用的其他实体类都要拥有完整的set、get、toString、构造器代码
 * @create 2022/7/28 16:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
@ConfigurationProperties(prefix = "person")
public class Person01 {

    private String lastName;
    private int age;
    private boolean boss;
    private Date birth;
    private Map<String, Object> maps;
    private List<String> lists;
    private Dog dog;

}
