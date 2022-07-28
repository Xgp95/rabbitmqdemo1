package rabbitmqtest.four;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import rabbitmqtest.util.MQUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Xugp
 * @date: 2022/7/25 17:35
 * @description:
 */
public class EmitLogTopic {
    private static final String EXCHANGE_NAME = "topic_log1";

    public static void main(String[] args) throws Exception {
        try (Channel channel = MQUtils.getChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            Map<String,String> bindingKeyMap = new HashMap<>();
            bindingKeyMap.put("quick.orange.rabbit","被队列 Q1Q2 接收到");
            bindingKeyMap.put("lazy.orange.elephant","被队列 Q1Q2 接收到");
            bindingKeyMap.put("quick.orange.fox","被队列 Q1 接收到");
            bindingKeyMap.put("lazy.brown.fox","被队列 Q2 接收到");
            bindingKeyMap.put("lazy.pink.rabbit","虽然满足两个绑定但只被队列 Q2 接收一次");
            bindingKeyMap.put("quick.brown.fox","不匹配任何绑定不会被任何队列接收到会被丢弃");
            bindingKeyMap.put("quick.orange.male.rabbit","是四个单词不匹配任何绑定会被丢弃");
            bindingKeyMap.put("lazy.orange.male.rabbit","是四个单词但匹配 Q2");
            for (Map.Entry<String, String> stringStringEntry : bindingKeyMap.entrySet()) {
                channel.basicPublish(EXCHANGE_NAME, stringStringEntry.getKey(), null,stringStringEntry.getValue().getBytes("utf-8"));
                System.out.println("发送消息:" + stringStringEntry.getValue());
            }

        }
    }
}
