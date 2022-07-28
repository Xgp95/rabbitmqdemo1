package rabbitmqtest.five;

import com.rabbitmq.client.*;
import rabbitmqtest.util.MQUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Xugp
 * @date: 2022/7/28 10:22
 * @description:
 */
public class Consumer1 {
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    private static final String DEAD_EXCHANGE = "dead_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = MQUtils.getChannel();

        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        String deadQueue = "dead_queue";
        channel.queueDeclare(deadQueue, false, false, false, null);
        channel.queueBind(deadQueue, DEAD_EXCHANGE, "ls");

        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "ls");
//        arguments.put("x-max-length", 5);
        String normalQueue = "normal_queue";
        channel.queueDeclare(normalQueue, false, false, false, arguments);
        channel.queueBind(normalQueue, NORMAL_EXCHANGE, "zs");

        System.out.println("Consumer1等待接收消息");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            if (msg.endsWith("1") || msg.endsWith("3") || msg.endsWith("7")) {
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
                System.out.println("Consumer1拒绝了：" + new String(message.getBody(), "UTF-8"));
            } else {
                System.out.println("Consumer1接收到：" + msg);
            }
        };
        CancelCallback cancelCallback = consumerTag -> {};
        channel.basicConsume(normalQueue, false, deliverCallback,cancelCallback);
    }
}
