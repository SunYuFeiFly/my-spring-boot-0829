package com.atguigu.elastic.bean;

import io.searchbox.annotations.JestId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author syf_12138
 * @Description jest操作elastic测试用实体类
 * @create 2022/8/4 15:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @JestId
    private Integer id;
    private String author;
    private String title;
    private String content;
}
