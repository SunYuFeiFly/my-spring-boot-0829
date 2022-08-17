package com.baizhi.security.entity;

import lombok.Data;
import java.util.List;

/**
 * @author syf_12138
 * @Description 菜单表实体类
 * @create 2022/8/17 14:58
 */

@Data
public class Menu {

    private Integer id;
    private String pattern;
    private List<Role> roles;
}
