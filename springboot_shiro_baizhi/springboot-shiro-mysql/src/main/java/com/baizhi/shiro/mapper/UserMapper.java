package com.baizhi.shiro.mapper;

import com.baizhi.shiro.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户注册
     * @param username 用户名
     * @param toHex md5加密后密码
     * @param salt 盐值
     */
    void addUser(@Param("username") String username, @Param("password") String password, @Param("salt") String salt);
}
