package com.baizhi.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * @author syf_12138
 * @Description 自定义MD5加密realm，返回的SimpleAuthenticationInfo信息中并未设置散列次数，相关数据在配置文件中定义
 * @create 2022/7/25 11:42
 */

public class MyMD5Realm extends AuthenticatingRealm {


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("获取用户名为：" + token.getPrincipal());
        // 校验用户名(实际开发中需从数据库查该用户名对应的数据)
        // 参数解析 参数1：查询数据后返回的用户名    参数2：查询数据后返回的加密密码    参数3：加密用的盐值   参数4：所用的realm
        if ("zhangsan".equals(token.getPrincipal())) {
            return new SimpleAuthenticationInfo("zhangsan", "c36ba81086532e0e418f05a93cbd4de2", ByteSource.Util.bytes("12139"), this.getName());
        }

        // 用户名不存在
        return null;
    }
}
