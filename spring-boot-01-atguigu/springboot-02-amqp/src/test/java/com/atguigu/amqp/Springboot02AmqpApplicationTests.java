package com.atguigu.amqp;

import com.atguigu.amqp.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02AmqpApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     * 1、单播，点对点（direct）
     */
    @Test
    public void testDirect() {
        // Message需要自己构造一个,定义消息体内容和消息头(不常用)
        // rabbitTemplate.send(exchage,routeKey,message);

        // object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq
        // rabbitTemplate.convertAndSend(exchage,routeKey,object);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "这是第一个消息");
        map.put("data", Arrays.asList("helloworld", 123, true));
        // 对象被默认序列化以后发送出去
        // rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",map);
        // rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",new Book("西游记","吴承恩"));
        rabbitTemplate.convertAndSend("exchange.topic", "*.news", new Book("西游记", "吴承恩"));
    }

    /**
     * 接受数据,如何将数据自动的转为json发送出去
     */
    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert("gulixueyuan.news");
        System.out.println(o);
    }

    /**
     * 2、广播(路由键不能祈祷匹配作用，所有人员都能收到)
     */
    @Test
    public void sendMsg() {
        rabbitTemplate.convertAndSend("exchange.fanout", "", new Book("红楼梦", "曹雪芹"));
    }

    /**
     * 创建交换机、队列,绑定
     */
    @Test
    public void createExchangedAndQuend() {
        // 创建交换机(持久化，不自动删除)
        amqpAdmin.declareExchange(new DirectExchange("amqpAdmin-exchange", true, false));
        // 创建队列(持久化)
        amqpAdmin.declareQueue(new Queue("amqpAdmin-Queue", true));
        // 交换机绑定队列
        // 参数1：目的地  参数2：目的地类型（绑定队列还是交换机） 参数3：交换机名称  参数:4：路由键  参数5：参数头信息
        amqpAdmin.declareBinding(new Binding("amqpAdmin-Queue", Binding.DestinationType.QUEUE, "amqpAdmin-exchange", "amqpAdmin.#", null));
    }


}
