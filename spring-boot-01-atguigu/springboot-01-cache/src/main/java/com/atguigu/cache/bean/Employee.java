package com.atguigu.cache.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author syf_12138
 * @Description 用户实体类
 * @create 2022/8/3 10:35
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    private Integer dId;
}
