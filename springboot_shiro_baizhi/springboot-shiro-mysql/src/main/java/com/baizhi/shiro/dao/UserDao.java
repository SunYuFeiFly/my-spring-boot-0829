package com.baizhi.shiro.dao;

import com.baizhi.shiro.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao extends BaseMapper<User> {

    /**
     * 根据用户名获取用户
     * @param username 用户名
     */
    User getUserByUsername(@Param("username") String username);

    /**
     * 用户注册
     * @param username 用户名
     * @param toHex md5加密后密码
     * @param salt 盐值
     */
    void addUser(String username, String toHex, String salt);
}
