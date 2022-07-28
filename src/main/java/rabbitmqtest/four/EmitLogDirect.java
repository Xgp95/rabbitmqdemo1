package rabbitmqtest.four;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import rabbitmqtest.util.MQUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author: Xugp
 * @date: 2022/7/25 17:15
 * @description:
 */
public class EmitLogDirect {
    private static final String EXCHANGE_NAME = "direct_log";

    public static void main(String[] args) throws Exception {
        try (Channel channel = MQUtils.getChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            Map<String,String> bindingKeyMap = new HashMap<>();
            bindingKeyMap.put("info","普通 info 信息");
            bindingKeyMap.put("warning","警告 warning 信息");
            bindingKeyMap.put("error","错误 error 信息");
            //debug 没有消费这接收这个消息 所有就丢失了
            bindingKeyMap.put("debug","调试 debug 信息");
            for (Map.Entry<String, String> stringStringEntry : bindingKeyMap.entrySet()) {
                channel.basicPublish(EXCHANGE_NAME, stringStringEntry.getKey(), null,stringStringEntry.getValue().getBytes("utf-8"));
                System.out.println("发送消息:" + stringStringEntry.getValue());
            }

        }
    }
}
