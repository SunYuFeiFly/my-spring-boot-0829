package com.atguigu.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author syf_12138
 * @Description 支付实体类
 * @create 2022/8/22 11:57
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;
    private String serial;
}
