package topic.provider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import topic.unils.RabbitMQUtil;

/**
 * @author syf_12138
 * @Description 消息提供者、动态路由
 * @create 2022/8/19 12:30
 */

public class Provider {

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            // 获取连接通信
            connection = RabbitMQUtil.getConnection();
            // 获取通信连接通道
            channel = connection.createChannel();
            // 通过通道声明交换机
            String exchangName = "topic-exchange";
            channel.exchangeDeclare(exchangName,"topic");
            // 发送消息
            String routingKey = "topics.info";
            // 发布消息
            // 参数1: 交换机名称
            // 参数2: 路由键
            // 参数3: 支持消息的其它属性
            // 参数4: 消息正文（需转成byte字节）
            channel.basicPublish(exchangName,routingKey,null,("这是一个来自direct类型交换机发给路由键为" + routingKey + "队列的信息").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("消息已发布");
            // 关闭资源
            RabbitMQUtil.closeConnection(channel, connection);
        }
    }
}
