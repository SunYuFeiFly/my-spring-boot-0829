package com.baizhi.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author syf_12138
 * @Description 消息消费者 - worker模式
 * @create 2022/8/19 15:53
 */

@Component
public class WorkerConsumer {

    /**
     * 第1个消费者
     */
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receiveWorker1(String message) {
        System.out.println("message1: " + message);
    }

    /**
     * 第2个消费者
     */
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receiveWorker2(String message) {
        System.out.println("message2: " + message);
    }
}
