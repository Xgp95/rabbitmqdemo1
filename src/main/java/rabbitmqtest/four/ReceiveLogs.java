package rabbitmqtest.four;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import rabbitmqtest.util.MQUtils;

/**
 * @author: Xugp
 * @date: 2022/7/25 17:00
 * @description:
 */
public class ReceiveLogs {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = MQUtils.getChannel();
        String queueName = channel.queueDeclare().getQueue();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        DeliverCallback deliverCallback = (consumerTag,message) -> {
            System.out.println("ReceiveLogs打印接收到的消息"+new String(message.getBody(), "UTF-8"));
        };
        CancelCallback cancelCallback = consumerTag -> {};
        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }
}
