package com.atguigu.shardingsphere.controller;

import com.atguigu.shardingsphere.entity.Course;
import com.atguigu.shardingsphere.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author syf_12138
 * @Description 操作控制类
 * @Date 2022/8/28 13:55
 **/

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("insert")
    public String insert(Course course) {
        int id = courseService.insert(course);
        return id + "";
    }
}
