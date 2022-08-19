package com.baizhi.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author syf_12138
 * @Description 消息消费者 - helloword模式
 *               @RabbitListener:指定监听
 *               queuesToDeclare = @Queue(value = "hello", durable = "true", exclusive = "true", autoDelete = "true") 指定监听具体队列(默认是持久化、非独占、不自动删除队列的)
 * @create 2022/8/19 15:19
 */

@Component
@RabbitListener(queuesToDeclare = @Queue(value = "helloword", durable = "true", exclusive = "true", autoDelete = "true"))
public class HelloWordConsumer {

    @RabbitHandler
    public void receiveHello(String message) {
        System.out.println("message: " + message);
    }
}
