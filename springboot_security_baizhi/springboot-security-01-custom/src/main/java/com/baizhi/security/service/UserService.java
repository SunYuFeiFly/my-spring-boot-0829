package com.baizhi.security.service;

import com.baizhi.security.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {

    /**
     * 根据用户名查找用户
     */
    User loadUserByUsername(@Param("username") String username);

}
