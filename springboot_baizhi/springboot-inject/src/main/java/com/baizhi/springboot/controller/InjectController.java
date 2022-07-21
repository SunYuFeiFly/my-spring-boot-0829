package com.baizhi.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author syf_12138
 * @Description 基本属性注入测试
 * @create 2022/7/21 14:51
 */

@RestController
@RequestMapping("inject")
public class InjectController {

    @Value("${name}")
    private String name;

    @Value("${age}")
    private Integer age;

    @Value("${weight}")
    private Double weight;

    @Value("${birthday}")
    private Date birthday;

    @Value("${sex}")
    private Boolean sex;

    /** 2.测试数组 */
    @Value("${arrays}")
    private String[] arrays;

    /** 3.测试集合 */
    @Value("${lists}")
    private List<String> lists;

    /** 注意：在注入map集合时，配置文件中要使用json格式，使用时必须使用"#{${xxx}}"进行获取 */
    @Value("#{${maps}}")
    private Map<String,String> maps;

    /**
     * 测试路径：http://localhost:8888/springboot-inject/inject/test
     */
    @RequestMapping("test")
    public String test(){
        System.out.println("inject ok");
        System.out.println("name = " + name);
        System.out.println("age = " + age);
        System.out.println("weight = " + weight);
        System.out.println("birthday = " + birthday);
        System.out.println("sex = " + sex);
        for (String array : arrays) {
            System.out.println("array = " + array);
        }
        for (String list : lists) {
            System.out.println("list = " + list);
        }
        maps.forEach((k,v)-> System.out.println("k = " + k + ", v = " + v));
        return "Inject Ok";
    }
}
