package com.baizhi.shiro.service;

import com.baizhi.shiro.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author syf_12138
 * @Description 用户服务接口
 * @create 2022/7/26 22:33
 */

public interface UserService extends IService<User> {

    /**
     * 注册用户
     * @param username 用户名
     * @param password md5加密后密码
     * @param salt 盐值
     */
    void addUser(String username, String password, String salt);
}
