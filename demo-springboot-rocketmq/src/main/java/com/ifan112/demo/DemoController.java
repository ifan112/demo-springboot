package com.ifan112.demo;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    // @Autowired
    // private DefaultMQProducer channelTestMQProducer;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/demo")
    public String demo() {
        rocketMQTemplate.syncSend("channel_test:tag_a", MessageBuilder.withPayload("测试" + Math.random()).build());

        // Message message = new Message();
        // double random = Math.random();
        // message.setBody(("来自springboot的测试，随机数" + random).getBytes());
        //
        // try {
        //     channelTestMQProducer.send(message);
        //     System.out.println("Send success.");
        // } catch (MQClientException e) {
        //     e.printStackTrace();
        // } catch (RemotingException e) {
        //     e.printStackTrace();
        // } catch (MQBrokerException e) {
        //     e.printStackTrace();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        return "DEMO";
    }
}
