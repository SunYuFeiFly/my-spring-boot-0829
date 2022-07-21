package com.baizhi.springboot.service.impl;

import com.baizhi.springboot.dao.UserMapper;
import com.baizhi.springboot.entity.User;
import com.baizhi.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author LiJian
 * @Date 2022/7/21 22:29
 **/

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        List<User> userList = userMapper.findAll();
        return userList;
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }
}
