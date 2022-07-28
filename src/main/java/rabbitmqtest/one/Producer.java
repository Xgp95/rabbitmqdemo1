package rabbitmqtest.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmqtest.util.MQUtils;

import java.io.IOException;

/**
 * @author: Xugp
 * @date: 2022/7/21 15:13
 * @description:
 */
public class Producer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("172.16.0.91");
//        factory.setUsername("admin");
//        factory.setPassword("123");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
        try(Channel channel = MQUtils.getChannel();){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "hello world";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("发送完毕！");
        }
    }
}
