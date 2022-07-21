package com.baizhi.springboot.dao;

import com.baizhi.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author LiJian
 * @Date 2022/7/21 22:30
 **/

@Mapper
public interface UserMapper {


    List<User> findAll();

    void save(User user);
}
