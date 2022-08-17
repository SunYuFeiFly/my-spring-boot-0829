package com.baizhi.security.controller;

import com.baizhi.security.entity.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * @author syf_12138
 * @Description 权限方法注解测试类（需要使用相应的注册功能需将相关共轭能打开：@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true))
 *              perPostEnabled: 开启 Spring Security 提供的四个权限注解，
 *                  @PostAuthorize： 在⽬前标⽅法执⾏之后进⾏权限校验。
 *                  @PostFiter： 在⽬标⽅法执⾏之后对⽅法的返回结果进⾏过滤。
 *                  @PreAuthorize：在⽬标⽅法执⾏之前进⾏权限校验。
 *                  @PreFiter：在⽬前标⽅法执⾏之前对⽅法参数进⾏过滤。
 *              securedEnabled: 开启 Spring Security 提供的 @Secured 注解⽀持（不⽀持权限表达式）
 *                  @Secured：访问⽬标⽅法必须具各相应的⻆⾊。
 *              jsr250Enabled: 开启 JSR-250 提供的注解，主要是@DenyAll、@PermitAll（不⽀持权限表达式）
 *                  @DenyAll：拒绝所有访问。
 *                  @PermitAll：允许所有访问。
 *                  @RolesAllowed：访问⽬标⽅法必须具备相应的⻆⾊。
 * @create 2022/8/17 11:46
 */

@RestController
@RequestMapping("/hello")
public class AuthorizeMethodController {

    /**
     * 当前访问用户具有ADMIN角色，且用户名是root（条件可以使用and、or）
     */
    @PreAuthorize("hasRole('ADMIN')  and authentication.name =='root'")
    // @PreAuthorize("hasAuthority('READ_INFO')")
    @GetMapping
    public String hello() {
        return "hello";
    }


    /**
     * 当前请求方法参数与访问用户用户名相同才能访问
     */
    @PreAuthorize("authentication.name==#name")
    @GetMapping("/name")
    public String hello(String name) {
        return "hello:" + name;
    }


    /**
     * 对请求的数据进行过滤
     * filterTarget 必须是数组集合类型
     * filterObject为集合中的每个元素
     */
    @PreFilter(value = "filterObject.id % 2 != 0", filterTarget = "users")
    @PostMapping("/users")
    public void addUsers(@RequestBody List<User> users) {
        System.out.println("users = " + users);
    }


    /**
     * 对返回的数据进行过滤，只有当参数id=1的数据会被返回
     */
    @PostAuthorize("returnObject.id==1")
    @GetMapping("/userId")
    public User getUserById(Integer id) {
        return new User(id, "blr");
    }

    /**
     * 对返回的数据有条件过滤返回
     */
    @PostFilter("filterObject.id % 2 == 0")//用来对方法返回值进行过滤
    @GetMapping("/lists")
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User(i, "blr:" + i));
        }
        return users;
    }


    /**
     * @Secured 只能判断角色
     */
    @Secured({"ROLE_USER"})
    @GetMapping("/secured")
    public User getUserByUsername() {
        return new User(99, "secured");
    }


    /**
     * @Secured 只能判断角色、具有其中一个即可
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/username")
    public User getUserByUsername2(String username) {
        return new User(99, username);
    }


    /**
     * 允许所有访问
     */
    @PermitAll
    @GetMapping("/permitAll")
    public String permitAll() {
        return "PermitAll";
    }


    /**
     * 拒绝所有访问
     */
    @DenyAll
    @GetMapping("/denyAll")
    public String denyAll() {
        return "DenyAll";
    }


    /**
     * 访问⽬标⽅法必须具备相应的⻆⾊（具有其中一个角色即可）
     */
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/rolesAllowed")
    public String rolesAllowed() {
        return "RolesAllowed";
    }


}
