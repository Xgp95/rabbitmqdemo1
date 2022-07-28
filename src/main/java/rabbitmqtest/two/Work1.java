package rabbitmqtest.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import rabbitmqtest.util.MQUtils;
import rabbitmqtest.util.SleepUtils;

/**
 * @author: Xugp
 * @date: 2022/7/23 15:06
 * @description:
 */
public class Work1 {
    private static final String QUEUE_NAME = "ack_queue1";

    public static void main(String[] args) throws Exception {
        Channel channel = MQUtils.getChannel();
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        System.out.println("worker1 等待接收消息....");
        CancelCallback cancelCallback = consumerTag -> System.out.println("消费者中断" + consumerTag);
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(1);
            System.out.println("worker1接收到消息:" + new String(message.getBody()));
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }
}
