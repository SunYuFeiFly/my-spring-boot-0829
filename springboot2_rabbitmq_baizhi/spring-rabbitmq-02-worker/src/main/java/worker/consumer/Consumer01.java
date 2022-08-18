package worker.consumer;

import com.rabbitmq.client.*;
import worker.utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author syf_12138
 * @Description 消息消费者（消费者自动确认消费，所以众多消费者均分消费，此消费者执行内容较少，可以很快执行完均分得到的消息，处于等待状态）
 * @create 2022/8/18 16:24
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
            // 通道绑定对应消息队列
            // 参数1: 队列名称(不存在的时候自动创建)
            // 参数2: 用来定义队列特征是要持久化
            // 参数3: 是否独占队列(true 就只能被当前连接使用)
            // 参数4: 是否在消费完成后自动删除队列
            // 参数5: 附加参数
            channel.queueDeclare("hello", false, false, false, null);
            // 接收消息
            // 参数1: 消费哪个对列的消息 队列名称
            // 参数2: 开启消息的自动确认机制
            // 参数3: 消费时的回调接口
            channel.basicConsume("hello", true, new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("接收到的消息是：" + new String(body));
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
