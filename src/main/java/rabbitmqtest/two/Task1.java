package rabbitmqtest.two;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import rabbitmqtest.util.MQUtils;

import java.util.Scanner;

/**
 * @author: Xugp
 * @date: 2022/7/23 15:02
 * @description:
 */
public class Task1 {
    public static final String QUEUE_NAME = "ack_queue1";

    public static void main(String[] args) throws Exception {
        try (Channel channel = MQUtils.getChannel();) {
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            Scanner scan = new Scanner(System.in);
            System.out.println("请输入：");
            while (scan.hasNext()) {
                String message = scan.next();
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println("发送完毕！" + message);
            }
        }
    }
}
