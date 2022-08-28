package com.atguigu.shardingsphere.service.impl;

import com.atguigu.shardingsphere.entity.Course;
import com.atguigu.shardingsphere.mapper.CourseMapper;
import com.atguigu.shardingsphere.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author syf_12138
 * @Description 服务接口实现类
 * @Date 2022/8/28 13:58
 **/

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;


    @Override
    public int insert(Course course) {
        int id = courseMapper.insert(course);
        return id;
    }
}
