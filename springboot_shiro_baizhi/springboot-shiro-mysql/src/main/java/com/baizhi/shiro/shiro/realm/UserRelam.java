package com.baizhi.shiro.shiro.realm;


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
 * @Description 自定义用户realm
 * @create 2022/7/26 12:35
 */

public class UserRelam extends AuthorizingRealm {

    /**
     * 权限授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();


        return simpleAuthorizationInfo;
    }


    /**
     * 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户名
        String username = (String) token.getPrincipal();
        System.out.println("用户名：" + token.getPrincipal());
        // 用户认证
        if ("zhangsan".equals(username)) {
            // 密码123456经过MD5加密并散列10次后结果为："c36ba81086532e0e418f05a93cbd4de2"
            return new SimpleAuthenticationInfo("zhangsan", "c36ba81086532e0e418f05a93cbd4de2", ByteSource.Util.bytes("12139"), this.getName());
        }

        // 验证不通过
        return null;
    }
}
