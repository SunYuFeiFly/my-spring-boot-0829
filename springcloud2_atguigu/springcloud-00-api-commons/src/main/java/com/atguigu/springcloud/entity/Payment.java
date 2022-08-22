package com.atguigu.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author syf_12138
 * @Description 公用实体类
 * @create 2022/8/22 15:01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    
    private Long id;
    private String serial;
}
