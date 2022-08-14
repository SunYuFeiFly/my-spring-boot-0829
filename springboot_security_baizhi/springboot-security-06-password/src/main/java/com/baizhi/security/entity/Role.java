package com.baizhi.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author syf_12138
 * @Description 角色实体类
 * @Date 2022/8/14 20:26
 **/

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer id;
    private String name;
    private String nameZh;
}
