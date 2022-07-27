package com.baizhi.shiro.mapper;

import com.baizhi.shiro.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色id集合获取用户相关权限集合
     * @param roleIdList 角色id集合
     * @return 用户相关权限集合
     */
    List<Permission> getPermissionsByRoleIds(@Param("roleIdList") List<Integer> roleIdList);
}
