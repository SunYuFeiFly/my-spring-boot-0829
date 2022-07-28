package com.atguigu.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author syf_12138
 * @Description 利用@value方式为对象属性注入值
 *                 如果类型是map，如果配置信息在yml，@ConfigurationProperties直接用${map}获取值即可，
 *                                                 @Value获取值需配置成person：maps：{'k1':'v1','k2':14}"类似形式,利用@Value(#{${map}})即可获取
 *                                如果配置在properties文件中利用@Value无法获取
 *                 引用对象无法使用@Value获取到对应对象
 * @create 2022/7/28 16:53
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class Person02 {

    @Value("${person.last-name}")
    private String lastName;
    @Value("${person.age}")
    private int age;
    @Value("${person.boss}")
    private boolean boss;
    @Value("${person.birth}")
    private Date birth;
    @Value("#{${person02.maps}}")
    private Map<String, Object> maps;
    @Value("${person.lists}")
    private List<String> lists;
    // @Value("${person.dog}")
    // private Dog dog;
}