package com.baizhi.security.service.impl;

import com.baizhi.security.entity.Role;
import com.baizhi.security.entity.User;
import com.baizhi.security.mapper.UserMapper;
import com.baizhi.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author syf_12138
 * @Description 用户服务接口实现类
 * @create 2022/8/10 17:39
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User loadUserByUsername(String username) {
        // 根据用户名查找用户
        User user = userMapper.loadUserByUsername(username);
        // 根据用户id查询角色集合
        List<Role> roleList = userMapper.getRolesByUid(user.getId());
        user.setRoles(roleList);

        return user;
    }
}
