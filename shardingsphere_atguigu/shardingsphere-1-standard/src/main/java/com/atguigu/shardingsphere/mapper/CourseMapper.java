package com.atguigu.shardingsphere.mapper;

import com.atguigu.shardingsphere.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author syf_12138
 * @Description 映射接口
 * @Date 2022/8/28 12:56
 **/

@Repository
public interface CourseMapper extends BaseMapper<Course> {

}
