package com.baizhi.shiro.service;

import com.baizhi.shiro.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author syf_12138
 * @Description 权限服务接口
 * @Date 2022/7/27 10.27
 */

public interface PermissionService extends IService<Permission> {

    /**
     * 根据角色id集合获取用户相关权限集合
     * @param roleIdList 角色id集合
     * @return 用户相关权限集合
     */
    List<Permission> getPermissionsByRoleIds(List<Integer> roleIdList);
}
