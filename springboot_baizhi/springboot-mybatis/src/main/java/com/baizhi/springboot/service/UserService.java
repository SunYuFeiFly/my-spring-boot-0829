package com.baizhi.springboot.service;

import com.baizhi.springboot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author syf_12138
 * @Date 2022/7/21 22:28
 **/

@Component
public interface UserService {


    List<User> findAll();

    void save(@Param("user") User user);
}
