package com.atguigu.amqp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author syf_12138
 * @Description 测试发送信息临时类
 * @create 2022/8/4 11:58
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String bookName;
    private String author;
}
