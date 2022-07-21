package com.baizhi.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author syf_12138
 * @Description 用户实体类
 * @Date 2022/7/21 22:23
 **/


@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private Integer id;
    private String name;
    private Date birthday;
    private Double salary;
}
