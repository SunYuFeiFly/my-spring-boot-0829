package com.baizhi.springboot.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author syf_12138
 * @Description 对象方式注入测试
 *      @ConfigurationProperties 注解
 *      修饰范围： 用来类上
 *      作用： 用来指定前缀的属性，注入到当前对象中属性名一致的属性中
 *      注意： 使用这个注解为属性一次性赋值，必须为属性提供set方法
 * @create 2022/7/21 15:21
 */

@RestController
@RequestMapping("injectObject")
@ConfigurationProperties(prefix = "orders")
public class InjectionObjectController {

    private Integer id;
    private String name;
    private Double price;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 测试路径：http://localhost:8888/springboot-inject/injectObject/test
     */
    @RequestMapping("test")
    public String test(){
        System.out.println("injectObject ok");
        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("price = " + price);
        return "injectObject ok";
    }
}
