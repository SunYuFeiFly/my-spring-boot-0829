package com.atguigu.elastic;

import com.atguigu.elastic.bean.Article;
import com.atguigu.elastic.bean.Book;
import com.atguigu.elastic.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03ElasticApplicationTests {

    @Autowired
    JestClient jestClient;

    @Autowired
    BookRepository bookRepository;

    /**
     * 测试springData操作elastic
     */
    @Test
    public void testElasticFoeSpringData() {
        Book book = new Book();
		book.setId(1);
		book.setBookName("西游记");
		book.setAuthor("吴承恩");
        bookRepository.index(book);
    }

    /**
     * 创建索引并保存一个文档
     * 类似localhost:9200\atguigu\article\{Id}保存数据
     */
    @Test
    public void contextLoads() {
        Article article = new Article();
        article.setId(1);
        article.setTitle("好消息");
        article.setAuthor("zhangsan");
        article.setContent("Hello World");

        // 构建一个索引功能
        Index index = new Index.Builder(article).index("atguigu").type("article").build();
        try {
            // 执行
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用jest进行搜索
     */
    @Test
    public void search() {
        // 构建搜索条件字符串
        String condition = "{\n" +
                " \"query\":{\n" +
                "  \"match\":{\n" +
                "   \"title\":\"好消息\"\n" +
                "  }\n" +
                " }\n" +
                "}";
        // 构建搜索功能
        Search search = new Search.Builder(condition).addIndex("atguigu").addType("article").build();
        try {
            // 执行
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 结果：
     * {
     * 	"took": 2,
     * 	"timed_out": false,
     * 	"_shards": {
     * 		"total": 5,
     * 		"successful": 5,
     * 		"skipped": 0,
     * 		"failed": 0
     *        },
     * 	"hits": {
     * 		"total": 1,
     * 		"max_score": 0.8630463,
     * 		"hits": [{
     * 			"_index": "atguigu",
     * 			"_type": "article",
     * 			"_id": "1",
     * 			"_score": 0.8630463,
     * 			"_source": {
     * 				"id": 1,
     * 				"author": "zhangsan",
     * 				"title": "好消息",
     * 				"content": "Hello World"
     *            }
     *        }]
     *    }
     * }
     */
}
