package com.atguigu.springboot;

import com.atguigu.springboot.bean.Person01;
import com.atguigu.springboot.bean.Person02;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot02ConfigApplicationTests {

    @Autowired
    Person01 person;

    @Autowired
    Person02 person02;

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

}
