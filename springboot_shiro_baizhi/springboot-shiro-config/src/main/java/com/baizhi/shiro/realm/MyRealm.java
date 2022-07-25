package com.baizhi.shiro.realm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * @author syf_12138
 * @Description 自定义验证规则
 * @create 2022/7/25 11:02
 */

public class MyRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("获取用户名：" + token.getPrincipal());
        // 此处应通过数据库查询用户名与前端传递数据对比
        if ("zhangsan".equals(token.getPrincipal())) {
            return new SimpleAuthenticationInfo("zhangsan", "123", this.getName());
        }

        // 说明用户名不存在
        return null;
    }
}
