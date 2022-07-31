package com.ati.rabbitmq.workqueues;

import com.ati.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * 生产者
 * 控制台输入
 */
public class Task01 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel();) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //从控制台当中接受信息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.next();
                /**
                 * 持久化属性
                 * MessageProperties.PERSISTENT_TEXT_PLAIN
                 */
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                /**
                 *
                 * 不公平分发
                 * channel.basicQos(1);
                 */
                int prefetchCount=1;
                channel.basicQos(prefetchCount);
                System.out.println("发送消息完成:" + message);
            }
        }
    }
}