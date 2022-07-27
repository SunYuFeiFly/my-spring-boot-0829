package com.baizhi.shiro.service.impl;


import com.baizhi.shiro.mapper.RoleMapper;
import com.baizhi.shiro.entity.Role;
import com.baizhi.shiro.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author syf_12138
 * @Description TODO
 * @create 2022/7/27 10:08
 */

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据用户id查询用户具有的角色名称集合
     * @param userId 用户id
     * @return List<Role> 用户角色集合
     */
    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        List<Role> roleList = roleMapper.getRolesByUserId(userId);
        return roleList;
    }
}
