package fanout.consumer;

import com.rabbitmq.client.*;
import fanout.utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description 消费者（广播模式，每个与交换机绑定的队列都能收到消息）
 * @create 2022/8/18 18:06
 */

public class Consumer01 {

    public static void main(String[] args) throws InterruptedException {
        Connection connection = null;
        Channel channel = null;
        try {
            // 获取mq连接对象
            connection = RabbitMQUtil.getConnection();
            // 获取通信连接通道
            channel = connection.createChannel();
            // 通道绑定交换机
            channel.exchangeDeclare("fanout-exchange","fanout");
            // 临时队列
            String queue = channel.queueDeclare().getQueue();
            // 绑定交换机和队列
            // 参数1: 队列名称
            // 参数2: 交换机名称
            // 参数3: 路由键(广播模式，所有与交换机帮绑定的队列都能收到生产者发送的消息)
            channel.queueBind(queue, "fanout-exchange","");
            // 接收消息（自动确认消息关闭）
            // 参数1: 消费哪个对列的消息 队列名称
            // 参数2: 开启消息的自动确认机制（不自动确认则需手动确认）
            // 参数3: 消费时的回调接口
            final Channel finalChannel = channel;
            channel.basicConsume(queue, false, new DefaultConsumer(finalChannel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("1号消费者接收到的消息是：" + new String(body));
                    // 手动确认（参数1：手动确认消息标识  参数2:每次确认1个）
                    finalChannel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            TimeUnit.MILLISECONDS.sleep(300000);
            System.out.println("消息已接收");
            RabbitMQUtil.closeConnection(channel, connection);
        }
    }
}
