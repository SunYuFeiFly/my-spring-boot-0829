package com.baizhi.shiro.service.impl;

import com.baizhi.shiro.dao.UserDao;
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

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名获取用户
     * @param username 用户名
     */
    @Override
    public User getUserByUsername(String username) {
        User user = userDao.getUserByUsername(username);
        return user;
    }


    /**
     * 注册用户
     * @param username 用户名
     * @param toHex md5加密后密码
     * @param salt 盐值
     */
    @Override
    public void addUser(String username, String toHex, String salt) {
        userDao.addUser(username, toHex, salt);

    }
}
