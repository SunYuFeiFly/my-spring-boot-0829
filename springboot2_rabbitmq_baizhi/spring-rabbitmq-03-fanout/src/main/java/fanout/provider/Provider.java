package fanout.provider;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fanout.utils.RabbitMQUtil;

/**
 * @author syf_12138
 * @Description 消息发送者（广播/扇出）
 * 1、可以有多个消费者
 * 2、每个消费者有自己的队列（queue）
 * 3、每个队列都要绑定交换机
 * 4、生产者发送消息到交换机，交换机决定发送到那些队列，生产者无法决定
 * 5、交换机把消息发送给所有绑定的队列
 * 6、队列的消费者都能获取消息
 * 实现一条消息被多个消费者消费
 * @create 2022/8/18 17:31
 */

public class Provider {

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMQUtil.getConnection();
            // 获取通信连接通道
            channel = connection.createChannel();
            // 通道绑定对应交换机
            // 参数1: 交换机名称(不存在的时候自动创建)
            // 参数2: 交换机类型
            channel.exchangeDeclare("fanout-exchange", "fanout");
            // 发布消息
            // 参数1: 交换机名称（此处通道直接连接队列，不用交换机）
            // 参数2:
            // 参数3:
            // 参数4: 消息的具体内容（需转成byte字节）
            channel.basicPublish("fanout-exchange", "", null, "hello fanout exchange".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("消息已发布");
            // 关闭资源
            RabbitMQUtil.closeConnection(channel, connection);
        }
    }
}
