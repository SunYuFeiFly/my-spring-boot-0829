package com.atguigu.elastic.repository;

import com.atguigu.elastic.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author syf_12138
 * @Description springData操作elastic快速接口类
 * @create 2022/8/4 15:31
 */

public interface BookRepository extends ElasticsearchRepository<Book,Integer>{

}
