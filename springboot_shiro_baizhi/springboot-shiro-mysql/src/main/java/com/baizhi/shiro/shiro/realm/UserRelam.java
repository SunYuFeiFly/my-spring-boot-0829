package com.baizhi.shiro.shiro.realm;

import com.baizhi.shiro.entity.User;
import com.baizhi.shiro.service.UserService;
import com.baizhi.shiro.utils.ApplicationContextUtils;
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
        // 获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();






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
        // 获取UserService服务类
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        // 根据用户名获取用户信息
        User user = userService.getUserByUsername(username);
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
