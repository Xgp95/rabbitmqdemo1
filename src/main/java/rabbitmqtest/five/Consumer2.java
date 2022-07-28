package rabbitmqtest.five;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import rabbitmqtest.util.MQUtils;

/**
 * @author: Xugp
 * @date: 2022/7/28 10:38
 * @description:
 */
public class Consumer2 {
    private static final String DEAD_EXCHANGE = "dead_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = MQUtils.getChannel();
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        String deadQueue = "dead_queue";
        channel.queueDeclare(deadQueue, false, false, false, null);
        channel.queueBind(deadQueue, DEAD_EXCHANGE, "ls");
        System.out.println("Consumer2等待接收消息");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Consumer2接收到：" + new String(message.getBody(),"UTF-8"));
        };
        CancelCallback cancelCallback = consumerTag -> {};
        channel.basicConsume(deadQueue, true, deliverCallback,cancelCallback);
    }
}
