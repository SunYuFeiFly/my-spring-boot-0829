package com.atguigu.elastic.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author syf_12138
 * @Description springData操作elastic用实体类
 * @create 2022/8/4 15:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "atguigu",type = "book")
public class Book {

    private Integer id;
    private String bookName;
    private String author;
}
