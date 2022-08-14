package com.baizhi.security.mapper;

import com.baizhi.security.entity.Role;
import com.baizhi.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author syf_12138
 * @Description 用户映射接口
 * @Date 2022/8/14 20:27
 **/

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查找用户
     */
    User loadUserByUsername(@Param("username") String username);

    /**
     * 根据用户id查询一个角色，注意一个用户可能不止一种角色
     */
    List<Role> getRolesByUid(@Param("uid") Integer uid);

    /**
     * 根据用户名更新密码方法
     */
    Integer updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);
}
