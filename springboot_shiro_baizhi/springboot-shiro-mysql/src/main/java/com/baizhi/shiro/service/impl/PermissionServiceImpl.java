package com.baizhi.shiro.service.impl;

import com.baizhi.shiro.mapper.PermissionMapper;
import com.baizhi.shiro.entity.Permission;
import com.baizhi.shiro.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author syf_12138
 * @Description TODO
 * @create 2022/7/27 10:05
 */

@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * @Author syf_12138
     * @Description 根据角色id集合获取用户相关权限集合
     * @param: roleIdList 角色id集合
     * @Return 用户相关权限集合
     * @Date 2022/7/27 12:59
     */
    @Override
    public List<Permission> getPermissionsByRoleIds(List<Integer> roleIdList) {
        List<Permission> permissionList = permissionMapper.getPermissionsByRoleIds(roleIdList);
        return permissionList;
    }
}
