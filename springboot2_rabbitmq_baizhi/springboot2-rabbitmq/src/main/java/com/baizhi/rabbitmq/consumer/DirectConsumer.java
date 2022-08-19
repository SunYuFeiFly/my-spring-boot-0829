package com.baizhi.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author syf_12138
 * @Description 消息消费者 - direct模式
 * @create 2022/8/19 16:36
 */

@Component
public class DirectConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name="direct-exchange",type = "direct"),
            key = {"info","error"}
    ))
    public void receiveDirect01(String message) {
        System.out.println("1号收到消息为：" + message);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name="direct-exchange",type = "direct"),
            key = {"info"}
    ))
    public void receiveDirect02(String message) {
        System.out.println("2号收到消息为：" + message);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name="direct-exchange",type = "direct"),
            key = {"error"}
    ))
    public void receiveDirect03(String message) {
        System.out.println("3号收到消息为：" + message);
    }
}
