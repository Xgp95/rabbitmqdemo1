package rabbitmqtest.one;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import rabbitmqtest.util.MQUtils;

/**
 * @author: Xugp
 * @date: 2022/7/21 15:53
 * @description:
 */
public class Worker {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = MQUtils.getChannel();
        System.out.println("worker2 等待接收消息....");
        CancelCallback cancelCallback = consumerTag -> System.out.println("消费者中断" + consumerTag);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println("接收到消息" + message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
