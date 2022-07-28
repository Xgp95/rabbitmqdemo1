package rabbitmqtest.five;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import rabbitmqtest.util.MQUtils;

/**
 * @author: Xugp
 * @date: 2022/7/27 17:29
 * @description:
 */
public class Produce {
    private static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {

        try (Channel channel = MQUtils.getChannel()){
            channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
//            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
            for (int i = 0; i < 11; i++) {
                String msg = "info" + i;
//                channel.basicPublish(NORMAL_EXCHANGE, "zs", properties, msg.getBytes());
                channel.basicPublish(NORMAL_EXCHANGE, "zs", null, msg.getBytes());
                System.out.println("发送消息:" + msg);
            }
        }
    }
}
