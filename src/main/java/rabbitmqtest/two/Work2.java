package rabbitmqtest.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import rabbitmqtest.util.MQUtils;
import rabbitmqtest.util.SleepUtils;

/**
 * @author: Xugp
 * @date: 2022/7/23 15:13
 * @description:
 */
public class Work2 {
    private static final String QUEUE_NAME = "ack_queue1";

    public static void main(String[] args) throws Exception {
        Channel channel = MQUtils.getChannel();
        int prefetchCount = 10;
        channel.basicQos(prefetchCount);
        System.out.println("worker2 等待接收消息....");
        CancelCallback cancelCallback = consumerTag -> System.out.println("消费者中断" + consumerTag);
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(15);
            System.out.println("worker2接收到消息:" + new String(message.getBody()));
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }
}
