package com.baizhi.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author syf_12138
 * @Description 角色实体类
 * @create 2022/8/11 14:17
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer id;
    private String name;
    private String nameZh;
}
