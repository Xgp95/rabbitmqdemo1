package rabbitmqtest.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import rabbitmqtest.util.MQUtils;

import java.util.UUID;

/**
 * @author: Xugp
 * @date: 2022/7/25 15:22
 * @description:
 */
public class PublishMessageBatch {

    public static int num = 1001;

    public static void main(String[] args) throws Exception {

        try (Channel channel = MQUtils.getChannel()) {
            channel.confirmSelect();
            String queueName = UUID.randomUUID().toString();
            channel.queueDeclare(queueName, true, false, false, null);
            long start = System.currentTimeMillis();
            for (int i = 0; i <= num; i++) {
                String message = "" + i;
                boolean flag = false;

                channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                if (i % 100 == 1 || i == num) {
                    flag = channel.waitForConfirms();
                }

                if (flag) {
                    System.out.println("消息发送成功" + message);
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("总共用时:" + (end - start));//120
        }
    }
}
