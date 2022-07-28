package com.atguigu.springboot;

import com.atguigu.springboot.bean.Person01;
import com.atguigu.springboot.bean.Person02;
import com.atguigu.springboot.bean.Person03;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot02ConfigApplicationTests {

    @Autowired
    Person01 person;

    @Autowired
    Person02 person02;

    @Autowired
    Person03 person03;

    @Autowired
    ApplicationContext ioc;

    @Test
    public void contextLoads() {
        System.out.println("person：" + person);
        for (String list : person.getLists()) {
            System.out.println("iter: " + list);
        }
    }

    @Test
    public void getPerson02() {
        System.out.println("person02:" + person02);
    }


    @Test
    public void getPerson03() {
        System.out.println("person03:" + person03);
    }


    /**
     * 用于测试ImportResource 导入配置文件功能
     */
    @Test
    public void testHelloService(){
        boolean b = ioc.containsBean("helloService");
        System.out.println(b);
    }

    /**
     * 用于测试@Configuration配置文件注入bean
     */
    @Test
    public void helloServic02(){
        boolean b = ioc.containsBean("helloService02");
        System.out.println(b);
    }

}
