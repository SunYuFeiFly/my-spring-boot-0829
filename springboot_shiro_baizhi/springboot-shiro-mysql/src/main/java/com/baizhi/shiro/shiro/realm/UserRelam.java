package com.baizhi.shiro.shiro.realm;

import com.baizhi.shiro.entity.Permission;
import com.baizhi.shiro.entity.Role;
import com.baizhi.shiro.entity.User;
import com.baizhi.shiro.service.PermissionService;
import com.baizhi.shiro.service.RoleService;
import com.baizhi.shiro.service.UserService;
import com.baizhi.shiro.utils.ApplicationContextUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.bcel.internal.generic.IFEQ;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author syf_12138
 * @Description 自定义用户realm
 * @create 2022/7/26 12:35
 */

public class UserRelam extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 权限授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进入授权阶段......");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("用户名为：" + username);
        // 获取用户信息
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        // 获取角色、权限服务类
        RoleService roleService = (RoleService) ApplicationContextUtils.getBean("roleService");
        PermissionService permissionService = (PermissionService) ApplicationContextUtils.getBean("permissionService");
        // 获取用户相关角色
        List<Role> roleList = roleService.getRolesByUserId(user.getId());
        System.out.println("roleList" + roleList);
        if (roleList.size() > 0) {
            for (Role role : roleList) {
                simpleAuthorizationInfo.addRole(role.getName());
            }
        }
        // 保存角色id集合，用于查询角色相关拥有权限集合
        List<Integer> roleIdList = roleList.stream().distinct().map(Role::getId).collect(Collectors.toList());
        // 根据角色id集合获取用户相关权限集合
        List<Permission> PermissionList = permissionService.getPermissionsByRoleIds(roleIdList);
        System.out.println("PermissionList" + PermissionList);
        if (PermissionList.size() > 0) {
            for (Permission permission : PermissionList) {
                simpleAuthorizationInfo.addStringPermission(permission.getName());
            }
        }

        return simpleAuthorizationInfo;
    }



    /**
     * 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("进入认证阶段......");
        // 获取用户名
        String username = (String) token.getPrincipal();
        System.out.println("用户名：" + token.getPrincipal());
        // 此处不涉及链表查询，直接用mybatisplus QueryWrapper查询较快
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        System.out.println("user: " + user);
        // 用户认证
        if (null != user) {
            return new SimpleAuthenticationInfo(user.getUsername(),
                    user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()),
                    this.getName());
        }

        // 验证不通过
        return null;
    }
}
