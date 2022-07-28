package rabbitmqtest.one;

import com.rabbitmq.client.*;
import rabbitmqtest.util.MQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: Xugp
 * @date: 2022/7/21 15:24
 * @description:
 */
public class Consumer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("172.16.0.91");
//        factory.setUsername("admin");
//        factory.setPassword("123");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
        try (Channel channel = MQUtils.getChannel();) {
            System.out.println("Consumer2 等待接收消息....");
            CancelCallback cancelCallback = consumerTag -> System.out.println("消费中断" + consumerTag);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                System.out.println("接收到消息" + message);
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
        }
    }
}
