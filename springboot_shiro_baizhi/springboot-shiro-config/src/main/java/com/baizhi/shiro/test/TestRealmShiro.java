package com.baizhi.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * @author syf_12138
 * @Description 测试自定义realm下shiro
 * @create 2022/7/25 11:12
 */

public class TestRealmShiro {

    public static void main(String[] args) {
        // 创建安全管理器工厂
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        // 通过工厂获取安全管理器
        SecurityManager securityManager = securityManagerFactory.getInstance();
        // 将安全管理器注入到Security工具类中
        SecurityUtils.setSecurityManager(securityManager);
        // 获取主体对象
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
        System.out.println("认证前状态：" + subject.isAuthenticated());
        try {
            // 登录操作
            subject.login(token);
        } catch (UnknownAccountException e) {
            System.out.println("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println("用户密码错误");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("认证后状态：" + subject.isAuthenticated());
    }
}
