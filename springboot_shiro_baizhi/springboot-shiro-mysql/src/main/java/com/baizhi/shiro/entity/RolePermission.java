package com.baizhi.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author syf_12138
 * @Description 角色-权限实体类
 * @create 2022/7/27 0:26
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("role_permission")
public class RolePermission {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
}
