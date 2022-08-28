package com.atguigu.shardingsphere.entity;

import lombok.Data;

/**
 * @Author syf_12138
 * @Description 实体类
 * @Date 2022/8/28 12:54
 **/

@Data
public class Course {

    private Long cid;
    private String cname;
    private Long userId;
    private String cstatus;
}
