package com.baizhi.shiro.service.impl;

import com.baizhi.shiro.mapper.UserMapper;
import com.baizhi.shiro.entity.User;
import com.baizhi.shiro.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author syf_12138
 * @Description 用户服务接口实现类
 * @create 2022/7/26 22:33
 */

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册用户
     * @param username 用户名
     * @param password md5加密后密码
     * @param salt 盐值
     */
    @Override
    public void addUser(String username, String password, String salt) {
        userMapper.addUser(username, password, salt);

    }
}
