package com.baizhi.rabbitmq.provider;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syf_12138
 * @Description 消息发送者
 * @create 2022/8/19 15:14
 */

@RestController
public class ProviderController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试helloword模型信息发送
     */
    @RequestMapping("/testHelloSend")
    public String testHelloSend(@RequestParam(value = "message") String message,
                                @RequestParam(value = "queueName") String queueName) {
        // 参数1： 队列名称
        // 参数2： 消息
        rabbitTemplate.convertAndSend(queueName, message);

        return "12138";
    }


    /**
     * 测试helloword模型信息发送（发送方式和helloword模型一样，并没有指定队列类型）
     */
    @RequestMapping("/testWorkerSend")
    public String testWorkerSend(@RequestParam(value = "message") String message,
                                 @RequestParam(value = "queueName") String queueName) {
        // 参数1： 队列名称
        // 参数2： 消息
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(queueName, message);
        }

        return "12138";
    }


    @RequestMapping("/testFanoutSend")
    public String testFanoutSend(@RequestParam(value = "message") String message,
                                 @RequestParam(value = "exchangeName") String exchangeName) {
        // 参数1： 交换机名称
        // 参数2： 路由键（worker模式不重要）
        // 参数3： 消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);

        return "12138";
    }


    @RequestMapping("/testDirectSend")
    public String testDirectSend(@RequestParam(value = "message") String message,
                                 @RequestParam(value = "exchangeName") String exchangeName,
                                 @RequestParam(value = "routingKey") String routingKey) {
        // 参数1： 交换机名称
        // 参数2： 路由键（worker模式不重要）
        // 参数3： 消息
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message + "[" + routingKey + "]");

        return "12138";
    }


    @RequestMapping("/testTopicSend")
    public String testTopicSend(@RequestParam(value = "message") String message,
                                 @RequestParam(value = "exchangeName") String exchangeName,
                                 @RequestParam(value = "routingKey") String routingKey) {
        // 参数1： 交换机名称
        // 参数2： 路由键（worker模式不重要）
        // 参数3： 消息
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message + "[" + routingKey + "]");

        return "12138";
    }
}
