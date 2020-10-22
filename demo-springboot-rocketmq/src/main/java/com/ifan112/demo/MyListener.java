package com.ifan112.demo;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = "channel_test",
        consumerGroup = "GID_channel_test",
        accessKey = "${rocketMQ.accessKey}",
        secretKey = "${rocketMQ.secretKey}",
        nameServer = "${rocketMQ.nameServerAddress}",
        selectorExpression = "tag_a"
)
public class MyListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("接收..");
        System.out.println(s);
    }
}
