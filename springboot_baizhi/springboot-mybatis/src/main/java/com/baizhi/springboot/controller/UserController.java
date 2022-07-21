package com.baizhi.springboot.controller;

import com.baizhi.springboot.entity.User;
import com.baizhi.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author syf_12138
 * @Date 2022/7/21 22:24
 **/

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("findAll")
    public List<User> findAll() {
        List<User> userList = userService.findAll();
        return userList;
    }

    @RequestMapping("save")
    public String save(User user) {
        userService.save(user);
        return "用户数据储存成功！";
    }
}
