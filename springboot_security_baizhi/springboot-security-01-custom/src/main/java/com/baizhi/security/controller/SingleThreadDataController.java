package com.baizhi.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 单线程状态下获取用户信息
 *              SecurityContextHolder介绍介绍：
 *                  Spring Security 会将登录用户数据保存在 Session 中，但是，为了使用方便，Spring Security在此基础上还做了一些改进，其中最主要的一个变化就是线程绑定：
 *              当用户登录成功后，Spring Security 会将登录成功的用户信息保存到 SecurityContextHolder 中。
 *                  SecurityContextHolder 中的数据保存默认是通过ThreadLocal 来实现的，使用 ThreadLocal 创建的变量只能被当前线程访问，不能被其他线程访问和修改，也就是用户数据和请求线程绑定在一起。
 *              当登录请求处理完毕后，Spring Security 会将 SecurityContextHolder 中的数据拿出来保存到 Session 中，同时将 SecurityContexHolder 中的数据清空。
 *              以后每当有请求到来时，Spring Security 就会先从 Session 中取出用户登录数据，保存到SecurityContextHolder 中，方便在该请求的后续处理过程中使用，
 *              同时在请求结束时将 SecurityContextHolder 中的数据拿出来保存到 Session 中，然后将SecurityContextHolder 中的数据清空。
 *              使用原理：
 *                  MODE THREADLOCAL：这种存放策略是将 SecurityContext 存放在 ThreadLocal中，大家知道 Threadlocal 的特点是在哪个线程中存储就要在哪个线程中读取，这其实非常适合 web 应用，
 *              因为在默认情况下，一个请求无论经过多少 Filter 到达 Servlet，都是由一个线程来处理的，这也是 SecurityContextHolder 的默认存储策略，这种存储策略意味着如果在具体的业务处理代码中，开启了子线程，在子线程中去获取登录用户数据，就会获取不到。
 * @create 2022/8/10 10:55
 */

@RestController
public class SingleThreadDataController {

    @RequestMapping("/singleThreadData")
    public String singleThreadData(){
        // 获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication.getAuthorities() = " + authentication.getAuthorities());
        System.out.println("authentication.getCredentials() = " + authentication.getCredentials());
        System.out.println("authentication.getDetails() = " + authentication.getDetails());
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());

        User user = (User) authentication.getPrincipal();
        System.out.println("principal.getUsername() = " + user.getUsername());
        System.out.println("principal.getPassword() = " + user.getPassword());
        System.out.println("principal.getAuthorities() = " + user.getAuthorities());
        return "hello spring security!";
    }
}
