package com.baizhi.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author syf_12138
 * @Description 消息消费者 - work模式
 * @create 2022/8/19 16:11
 */

@Component
public class FanoutConsumer {

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "fanout-exchange", type = "fanout")
            )
    )
    public void receive01(String Message) {
        System.out.println("1号接收到消息为：" + Message);
    }


    @RabbitListener(
            bindings = @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "fanout-exchange", type = "fanout")
            )
    )
    public void receive02(String Message) {
        System.out.println("2号接收到消息为：" + Message);
    }
}
