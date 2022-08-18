package fanout.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author syf_12138
 * @Description Rabbit工具类
 * @create 2022/8/18 17:54
 */

public class RabbitMQUtil {

    private static ConnectionFactory factory;

    static {
        // 工厂值创建一次
        factory = new ConnectionFactory();
        factory.setHost("192.168.1.199");
        factory.setPort(5672);
        factory.setVirtualHost("/ems");
        factory.setUsername("ems");
        factory.setPassword("123456");
    }

    /**
     * 获取RabbitMQ的连接
     * @return 连接对象
     */
    public static Connection getConnection(){
        try {
            Connection connection = factory.newConnection();
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接   注意：一般消费者不用关闭连接 只用于生产者关闭连接
     */
    public static void closeConnection(Channel channel, Connection connection){
        try {
            if (channel != null){
                channel.close();
            }
            if (connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
