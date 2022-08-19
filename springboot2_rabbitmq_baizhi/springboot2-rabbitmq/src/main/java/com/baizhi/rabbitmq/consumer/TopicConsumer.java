package com.baizhi.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author syf_12138
 * @Description 消息消费者 - topic模式
 * @create 2022/8/19 16:51
 */

@Component
public class TopicConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name="topic-exchange",type = "topic"),
            key = {"user.info"}
    ))
    public void receiveTopic01(String message) {
        System.out.println("1号收到消息为：" + message);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name="topic-exchange",type = "topic"),
            key = {"user.*"}
    ))
    public void receiveTopic02(String message) {
        System.out.println("2号收到消息为：" + message);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name="topic-exchange",type = "topic"),
            key = {"*.info"}
    ))
    public void receiveTopic03(String message) {
        System.out.println("3号收到消息为：" + message);
    }
}
