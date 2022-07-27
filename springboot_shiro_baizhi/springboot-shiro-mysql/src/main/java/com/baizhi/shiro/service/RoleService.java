package com.baizhi.shiro.service;

import com.baizhi.shiro.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author syf_12138
 * @Description 角色服务接口
 * @Date 2022/7/27 10.25
 */

public interface RoleService extends IService<Role> {

    /**
     * 根据用户id查询用户具有的角色名称集合
     * @param userId 用户id
     * @return List<Role> 用户角色集合
     */
    List<Role> getRolesByUserId(Integer userId);
}
