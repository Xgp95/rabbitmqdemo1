package rabbitmqtest.four;

import com.rabbitmq.client.Channel;
import rabbitmqtest.util.MQUtils;

import java.util.Scanner;

/**
 * @author: Xugp
 * @date: 2022/7/25 16:54
 * @description:
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {

        try (Channel channel = MQUtils.getChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入：");
            while (scanner.hasNext()) {
                String message = scanner.next();
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("utf-8"));
                System.out.println("发送消息：" + message);
            }
        }
    }
}
