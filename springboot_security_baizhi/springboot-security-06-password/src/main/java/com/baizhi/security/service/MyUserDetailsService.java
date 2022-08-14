package com.baizhi.security.service;

import com.baizhi.security.entity.Role;
import com.baizhi.security.entity.User;
import com.baizhi.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Author syf_12138
 * @Description 自定义用户细节服务接口实现类
 * @Date 2022/8/14 20:29
 **/

@Component
public class MyUserDetailsService implements UserDetailsService {

    private UserMapper userMapper;

    @Autowired
    public MyUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查找用户
        User user = userMapper.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new RuntimeException("用户名不存在");
        }
        // 根据用户id查询角色集合
        List<Role> roleList = userMapper.getRolesByUid(user.getId());
        user.setRoles(roleList);

        return user;
    }
}
