package worker.consumer;

import com.rabbitmq.client.*;
import worker.utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description 消息消费者（将消息确认设置为false，手动确认确保任务整体完成，且设置消费者每次只能消费一个消息，这样就能实现吃完再拿，即能者多劳）
 * @create 2022/8/18 16:56
 */

public class Consumer02 {

    public static void main(String[] args) throws InterruptedException {
        Connection connection = null;
        Channel channel = null;
        try {
            // 获取mq连接对象
            connection = RabbitMQUtil.getConnection();
            // 获取通信连接通道
            channel = connection.createChannel();
            // 通道绑定对应消息队列
            // 参数1: 队列名称(不存在的时候自动创建)
            // 参数2: 用来定义队列特征是要持久化
            // 参数3: 是否独占队列(true 就只能被当前连接使用)
            // 参数4: 是否在消费完成后自动删除队列
            // 参数5: 附加参数
            channel.queueDeclare("hello", false, false, false, null);
            // 设置消费者每次只能消费一个消息
            channel.basicQos(1);
            // 接收消息（自动确认消息关闭）
            // 参数1: 消费哪个对列的消息 队列名称
            // 参数2: 开启消息的自动确认机制(自动确认的话接收到任务就向rabbiutmq反馈任务处理了，并不涉及任务实际有没有处理完成)
            // 参数3: 消费时的回调接口
            final Channel finalChannel = channel;
            channel.basicConsume("hello", false, new DefaultConsumer(finalChannel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("接收到的消息是：" + new String(body));
                    // 手动确认（参数1：手动确认消息标识  参数2:每次确认1个）
                    finalChannel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            TimeUnit.MILLISECONDS.sleep(3000);
            System.out.println("消息已接收");
            // RabbitMQUtil.closeConnection(channel, connection);
        }
    }
}
