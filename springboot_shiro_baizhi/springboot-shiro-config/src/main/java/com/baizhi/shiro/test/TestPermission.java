package com.baizhi.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/**
 * @author syf_12138
 * @Description 用户认证、权限测试
 * @create 2022/7/25 16:13
 */

public class TestPermission {

    public static void main(String[] args) {
        // 创建安全管理器工厂
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        // 通过工厂获取安全管理器
        SecurityManager securityManager = securityManagerFactory.getInstance();
        // 将安全管理器注入到Security工具类中
        SecurityUtils.setSecurityManager(securityManager);
        // 获取主体对象
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("lisi", "123");
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
        // 授权过程
        if (subject.isAuthenticated()) {
            // 基于角色授权（需在认证通过后进行）
            System.out.println("当前用户是否具有super角色：" + (subject.hasRole("super") ? "具有" : "不具有"));
            System.out.println("当前用户是否同时具有normal、super角色：" + (subject.hasAllRoles(Arrays.asList("normal", "super")) ? "具有" : "不具有"));

            // 基于资源授权，灵活性更强
            System.out.println("当前用户是否具有user模块创建资源权限：" + (subject.isPermitted("user:create") ? "具有" : "不具有"));
            System.out.println("当前用户是否具有product模块创建资源权限：" + (subject.isPermitted("product:create") ? "具有" : "不具有"));

            System.out.println("当前用户是否同时具有user、product模块创建资源权限: " + (subject.isPermittedAll("user:create", "product:create") ? "具有" : "不具有"));
        }
    }

}
