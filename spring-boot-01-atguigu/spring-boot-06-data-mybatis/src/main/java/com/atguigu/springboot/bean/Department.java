package com.atguigu.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author syf_12138
 * @Description 部门实体类
 * @create 2022/8/2 15:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    private Integer id;
    private String departmentName;
}
