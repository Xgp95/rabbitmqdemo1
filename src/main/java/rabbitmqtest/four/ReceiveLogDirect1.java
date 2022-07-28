package rabbitmqtest.four;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import rabbitmqtest.util.MQUtils;

/**
 * @author: Xugp
 * @date: 2022/7/25 17:22
 * @description:
 */
public class ReceiveLogDirect1 {
    private static final String EXCHANGE_NAME = "direct_log";

    public static void main(String[] args) throws Exception {
        Channel channel = MQUtils.getChannel();
        String queueName = "ReceiveLogDirect1";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME, "info");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogDirect1打印接收到的消息"+new String(message.getBody(), "UTF-8"));
        };
        CancelCallback cancelCallback = consumerTag -> {};
        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }
}
