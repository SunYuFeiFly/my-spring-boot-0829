package com.baizhi.shiro.controller;

import com.baizhi.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author syf_12138
 * @Description 用户控制层
 * @create 2022/7/26 16:28
 */

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * @Author syf_12138
     * @Description 用户登录控制
     * @param user 登录用户信息
     * @Date 2022/7/26 16:30
     */
    @RequestMapping("login")
    public String login(User user) {
        System.out.println("user: " + user);
        // 通过安全管理器工具类获取主体对象
        Subject subject = SecurityUtils.getSubject();
        // 登录验证
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(token);
            // 认证成功，跳转主页面
            return "redirect:/index.jsp";
        } catch (UnknownAccountException e) {
            System.out.println("用户名不存在！");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码不正确");
        }

        // 用户认证失败，跳转登陆页面
        return "redirect:/main/login.jsp";
    }

    @RequestMapping("register")
    public String register() {
        // 用户注册操作

        return "register";
    }

    @RequestMapping("logout")
    public String logout() {
        // 通过安全工具类，获取主体信息并登出
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }

        // 重定向到登录页面
        return "redirect:/main/login.jsp";
    }
}
