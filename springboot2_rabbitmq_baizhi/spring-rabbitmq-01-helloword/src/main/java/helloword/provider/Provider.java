package helloword.provider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import helloword.utils.RabbitMQUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author syf_12138
 * @Description 消息提供者
 * @create 2022/8/18 15:08
 */

public class Provider {

    @Test
    public void testSendMessage() throws IOException, TimeoutException {

        Connection connection = null;
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
            // 发布消息
            // 参数1: 交换机名称（此处通道直接连接队列，不用交换机）
            // 参数2: 队列名称
            // 参数3: 传递参数的额外设置(发送的消息不会因为服务器重启而丢失)
            // 参数4: 消息的具体内容（需转成byte字节）
            channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rbbitmq_12138".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("消息已发布");
            // 关闭资源
            channel.close();
            connection.close();
        }
    }


    @Test
    public void testSendMessageUpgrade() throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
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
            // 发布消息
            // 参数1: 交换机名称（此处通道直接连接队列，不用交换机）
            // 参数2: 队列名称
            // 参数3: 传递参数的额外设置
            // 参数4: 消息的具体内容（需转成byte字节）
            channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rbbitmq_12138".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("消息已发布");
            // 关闭资源
            RabbitMQUtil.closeConnection(channel, connection);
        }
    }
}