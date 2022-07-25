package com.baizhi.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author syf_12138
 * @Description 自定义realm，实现认证授权（最终）
 * @create 2022/7/25 17:33
 */

public class CustomerRealm extends AuthorizingRealm {

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 通过身份集合获取用户主身份（用户名）
        Object userName = principalCollection.getPrimaryPrincipal();
        // 通过用户名查询数据库关联角色、资源权限集合（此处省略）
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 设置角色
        authorizationInfo.addRole("normal");
        authorizationInfo.addRole("super");
        // 设置资源权限
        authorizationInfo.addStringPermission("user:create:001");

        return authorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if ("zhangsan".equals(token.getPrincipal())) {
            return new SimpleAuthenticationInfo("zhangsan", "63fb3732e1e7a9a4d056f0523ec4cd20", ByteSource.Util.bytes("12139"), this.getName());
        }

        // 用户名不存在，认证不通过
        return null;
    }
}
