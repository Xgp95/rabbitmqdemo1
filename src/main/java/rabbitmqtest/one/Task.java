package rabbitmqtest.one;

import com.rabbitmq.client.Channel;
import rabbitmqtest.util.MQUtils;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author: Xugp
 * @date: 2022/7/21 15:43
 * @description:
 */
public class Task {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        try (Channel channel = MQUtils.getChannel();) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Scanner scan = new Scanner(System.in);
            while (scan.hasNext()) {
                String message = scan.next();
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("发送完毕！" + message);
            }
        }
    }
}
