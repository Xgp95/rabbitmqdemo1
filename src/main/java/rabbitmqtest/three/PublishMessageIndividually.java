package rabbitmqtest.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import rabbitmqtest.util.MQUtils;

import java.util.UUID;

/**
 * @author: Xugp
 * @date: 2022/7/25 15:10
 * @description:
 */
public class PublishMessageIndividually {
    public static void main(String[] args) throws Exception {
        try (Channel channel = MQUtils.getChannel()) {
            channel.confirmSelect();
            String queueName = UUID.randomUUID().toString();
            channel.queueDeclare(queueName, true, false, false, null);
            long start = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                String message = "" + i;
                channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                boolean flag = channel.waitForConfirms();
                if (flag) {
                    System.out.println("消息发送成功" + message);
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("总共用时:" + (end - start));//873
        }
    }
}
