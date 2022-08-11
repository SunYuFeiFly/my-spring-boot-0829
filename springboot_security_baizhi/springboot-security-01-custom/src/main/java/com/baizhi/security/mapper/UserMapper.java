package com.baizhi.security.mapper;

import com.baizhi.security.entity.Role;
import com.baizhi.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author syf_12138
 * @Description 用户映射接口
 * @create 2022/8/10 17:36
 */

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
}
