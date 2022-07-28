package rabbitmqtest.four;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import rabbitmqtest.util.MQUtils;

/**
 * @author: Xugp
 * @date: 2022/7/25 17:37
 * @description:
 */
public class ReceiveLogTopic2 {
    private static final String EXCHANGE_NAME = "topic_log1";

    public static void main(String[] args) throws Exception {
        Channel channel = MQUtils.getChannel();
        String queueName = "ReceiveLogTopic2";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME,   "*.*.rabbit");
        channel.queueBind(queueName, EXCHANGE_NAME, "lazy.#");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogTopic2打印接收到的消息"+new String(message.getBody(), "UTF-8"));
        };
        CancelCallback cancelCallback = consumerTag -> {};
        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }
}
