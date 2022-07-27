package com.baizhi.shiro.mapper;

import com.baizhi.shiro.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取角色集合
     * @param userId 用户id
     * @return 角色集合
     */
    List<Role> getRolesByUserId(@Param("userId") Integer userId);
}
