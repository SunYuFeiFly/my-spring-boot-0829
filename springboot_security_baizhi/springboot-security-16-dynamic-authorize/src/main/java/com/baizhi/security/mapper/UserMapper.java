package com.baizhi.security.mapper;

import com.baizhi.security.entity.Role;
import com.baizhi.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author syf_12138
 * @Description 用户映射接口类
 * @create 2022/8/17 15:02
 */

@Mapper
public interface UserMapper {

    /**
     * 根据用户 id 获取角色信息
     */
    List<Role> getUserRoleByUid(@Param("uid") Integer uid);

    /**
     * 根据用户名获取用户信息
     */
    User loadUserByUsername(@Param("username") String username);
}
