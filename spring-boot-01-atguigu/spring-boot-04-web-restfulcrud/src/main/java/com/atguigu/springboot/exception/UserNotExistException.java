package com.atguigu.springboot.exception;

/**
 * @author syf_12138
 * @Description 自定义异常
 * @create 2022/8/2 11:56
 */

public class UserNotExistException extends RuntimeException {

    public UserNotExistException() {
        super("用户不存在");
        System.out.println("用户不存在");
    }
}
