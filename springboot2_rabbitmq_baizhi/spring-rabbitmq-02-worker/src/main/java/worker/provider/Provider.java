package worker.provider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import worker.utils.RabbitMQUtil;

/**
 * @author syf_12138
 * @Description 消息提供者（当前情况为消息全部发送，不限制每次消费条数，而消费者自动确认消费，所以造成所有消费者均分消费消息）
 * @create 2022/8/18 16:12
 */

public class Provider {

    public static void main(String[] args) {
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
            for (int i = 0; i < 100; i++) {
                channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, (i + "hello rbbitmq_12138").getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("消息已发布");
            // 关闭资源
            RabbitMQUtil.closeConnection(channel, connection);
        }


    }
}
