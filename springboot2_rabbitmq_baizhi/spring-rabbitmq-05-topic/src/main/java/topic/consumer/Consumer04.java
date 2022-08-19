package topic.consumer;

import com.rabbitmq.client.*;
import topic.unils.RabbitMQUtil;

import java.io.IOException;

/**
 * @author syf_12138
 * @Description 消息消费者、动态路由
 * @create 2022/8/19 12:30
 */

public class Consumer04 {

    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = RabbitMQUtil.getConnection();
        // 获取通信连接通道
        final Channel channel = connection.createChannel();
        // 通道声明交换机及交换机类型
        channel.exchangeDeclare("topic-exchange", "topic");
        // 创建一个临时队列
        String queue = channel.queueDeclare().getQueue();
        // 通道基于路由键绑定队列和交换机（该队列只能获取交换机（direct-exchange）中路由键（info）对应的信息）
        // 参数1： 队列
        // 参数2： 交换机
        // 参数3： 路由键
        channel.queueBind(queue, "topic-exchange", "*.info");
        // 接收消息
        // 参数1： 队列
        // 参数2： 是否主动确认接收
        // 参数3： 消费时的回调接口
        channel.basicConsume(queue, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("4号消费者获取到信息为：" + new String(body));
                // 确认消息
                // 手动确认（参数1：手动确认消息标识  参数2:每次确认1个）
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
