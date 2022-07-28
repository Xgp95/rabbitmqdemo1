package rabbitmqtest.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import rabbitmqtest.util.MQUtils;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author: Xugp
 * @date: 2022/7/25 15:33
 * @description:
 */
public class PublishMessageAsync {
    public static int num = 1001;

    public static void main(String[] args) throws Exception {
        try (Channel channel = MQUtils.getChannel()) {
            channel.confirmSelect();
            String queueName = UUID.randomUUID().toString();
            channel.queueDeclare(queueName, true, false, false, null);

            ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

            ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
                if (multiple) {
                    ConcurrentNavigableMap<Long, String> longStringConcurrentNavigableMap = outstandingConfirms.headMap(deliveryTag, true);
                    longStringConcurrentNavigableMap.clear();
                } else {
                    outstandingConfirms.remove(deliveryTag);
                }
            };
            ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
                String message = outstandingConfirms.get(deliveryTag);
                System.out.println("发布的消息" + message + "未被确认，序列号" + deliveryTag);
            };

            channel.addConfirmListener(ackCallback, nackCallback);

            long start = System.currentTimeMillis();
            for (int i = 0; i < num; i++) {
                String message = "" + i;
                outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
                channel.basicPublish("", queueName, null, message.getBytes());
            }
            long end = System.currentTimeMillis();
            System.out.println("总共用时:" + (end - start));//873/120/48
        }
    }
}
