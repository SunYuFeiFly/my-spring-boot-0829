package com.baizhi.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 多线程下获取用户信息
 *              SecurityContextHolder介绍介绍：
 *                  Spring Security 会将登录用户数据保存在 Session 中，但是，为了使用方便，Spring Security在此基础上还做了一些改进，其中最主要的一个变化就是线程绑定：
 *              当用户登录成功后，Spring Security 会将登录成功的用户信息保存到 SecurityContextHolder 中。
 *                  SecurityContextHolder 中的数据保存默认是通过ThreadLocal 来实现的，使用 ThreadLocal 创建的变量只能被当前线程访问，不能被其他线程访问和修改，也就是用户数据和请求线程绑定在一起。
 *              当登录请求处理完毕后，Spring Security 会将 SecurityContextHolder 中的数据拿出来保存到 Session 中，同时将 SecurityContexHolder 中的数据清空。
 *              以后每当有请求到来时，Spring Security 就会先从 Session 中取出用户登录数据，保存到SecurityContextHolder 中，方便在该请求的后续处理过程中使用，
 *              同时在请求结束时将 SecurityContextHolder 中的数据拿出来保存到 Session 中，然后将SecurityContextHolder 中的数据清空。
 *              使用原理：
 *                  MODE INHERITABLETHREADLOCAL：这种存储模式适用于多线程环境，如果希望在子线程中也能够获取到登录用户数据，那么就可以使用这种存储模式，
 *              使用时需要配置spring.security.strategy参数属性值（实际上是将父线程的用户数据复制一份到子线程），两种方法：
 *                  1）、在VM Options单独进行配置： -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL
 *                  2）、在项目主启动类run()方法之前设置全局参数
 *                      System.setProperty("spring.security.strategy","MODE_INHERITABLETHREADLOCAL");
 *                  3)、在项目主启动类run()方法之后设置SecurityContextHolder属性
 *                      SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL)之后再使用;
 *
 * @create 2022/8/10 11:00
 */

@RestController
public class MultiThreadDataController {

    @RequestMapping("/multiThreadData")
    public String multiThreadData(){
        // 开启子线程
        new Thread(() -> {
            // 获取认证信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            System.out.println("principal.getUsername() = " + user.getUsername());
            System.out.println("principal.getPassword() = " + user.getPassword());
            System.out.println("principal.getAuthorities() = " + user.getAuthorities());
        }, "子线程").start();

        return "hello spring security!";
    }
}
