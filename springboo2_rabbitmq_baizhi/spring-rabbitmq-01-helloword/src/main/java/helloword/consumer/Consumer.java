package helloword.consumer;

import com.rabbitmq.client.*;
import helloword.utils.RabbitMQUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author syf_12138
 * @Description 消费收费者
 * @create 2022/8/18 10:56
 */

public class Consumer {


    @Test
    public void testGetMessage() throws IOException, TimeoutException, InterruptedException {
        Connection connection = null;
        // 获取通信连接通道
        Channel channel = null;
        try {
            // 创建连接mq的工厂对象
            ConnectionFactory connectionFactory = new ConnectionFactory();
            // 设置连接的mq主机地址
            connectionFactory.setHost("192.168.1.199");
            // 设置连接哪个虚拟主机
            connectionFactory.setVirtualHost("/ems");
            // 设置连接主机端口号
            connectionFactory.setPort(5672);
            // 设置连接mq用户名密码
            connectionFactory.setUsername("ems");
            connectionFactory.setPassword("123456");
            // 获取mq连接对象
            connection = connectionFactory.newConnection();
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
                    System.out.println("接收到的消息是：" + String.valueOf(body));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("消息已接收");
            channel.close();
            connection.close();
        }
    }

    @Test
    public void testGetMessageUpgrade() throws IOException, TimeoutException, InterruptedException {
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
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("消息已接收");
            RabbitMQUtil.closeConnection(channel, connection);
        }
    }
}
