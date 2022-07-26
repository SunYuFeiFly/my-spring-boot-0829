package com.baizhi.shiro.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author syf_12138
 * @Description 用户实体类
 * @create 2022/7/26 16:31
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("blog_user")
public class User {

    private String id;
    private String username;
    private String password;
    private String salt;
}
