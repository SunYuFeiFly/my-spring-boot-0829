package com.atguigu.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author syf_12138
 * @Description 用户实体类
 * @create 2022/8/2 15:10
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Integer id;
    private String lastName;
    private Integer gender;
    private String email;
    private Integer dId;
}
