// package com.ifan112.demo;
//
// import org.apache.rocketmq.acl.common.AclClientRPCHook;
// import org.apache.rocketmq.acl.common.SessionCredentials;
// import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
// import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
// import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
// import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
// import org.apache.rocketmq.client.exception.MQClientException;
// import org.apache.rocketmq.client.producer.DefaultMQProducer;
// import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
// import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
// import org.apache.rocketmq.remoting.RPCHook;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.stereotype.Service;
//
// @Service
// public class RocketMQConsumer {
//
//     @Value("${rocketMQ.accessKey}")
//     private String rocketMQAccessKey;
//
//     @Value("${rocketMQ.secretKey}")
//     private String rocketMQSecretKey;
//
//     @Value("${rocketMQ.nameServerAddress}")
//     private String rocketMQnameServerAddress;
//
//     private static final String TOPIC = "channel_test";
//     private static final String TAG = "tag_a";
//
//     @Bean
//     public RPCHook aclClientRPCHook() {
//         return new AclClientRPCHook(new SessionCredentials(rocketMQAccessKey, rocketMQSecretKey));
//     }
//
//     @Bean
//     public MessageListenerConcurrently messageListenerConcurrently() {
//         // 匿名消费者
//         return (msgs, context) -> {
//             System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
//             System.out.printf("Messages Size: %d %n", msgs.size());
//
//             String msgBody = new String(msgs.get(0).getBody());
//             System.out.printf("Message Body: %s %n", msgBody);
//
//             // return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//
//             double random = Math.random();
//             System.out.printf("random: %f %n", random);
//
//             if (random > 0.5) {
//                 System.out.println("消费成功");
//                 return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//             } else {
//                 System.out.println("消费失败");
//                 return null;
//             }
//         };
//     }
//
//     @Bean
//     public DefaultMQPushConsumer channelTestMQConsumer(
//             RPCHook aclClientRPCHook, MessageListenerConcurrently messageListener) throws MQClientException {
//         String consumerGroupId = "GID_channel_test";
//
//         DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroupId, aclClientRPCHook,
//                 new AllocateMessageQueueAveragely(), true, null);
//
//         consumer.setNamesrvAddr(rocketMQnameServerAddress);
//         consumer.subscribe(TOPIC, TAG);
//
//         consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//         // 单次只消费一条消息
//         consumer.setConsumeMessageBatchMaxSize(1);
//         // 集群消费模式
//         consumer.setMessageModel(MessageModel.CLUSTERING);
//
//         consumer.registerMessageListener(messageListener);
//
//         consumer.start();
//         System.out.printf("Consumer Started.%n");
//
//         return consumer;
//     }
//
//     @Bean
//     public DefaultMQProducer channelTestMQProducer() throws MQClientException {
//         DefaultMQProducer producer = new DefaultMQProducer();
//         producer.setNamesrvAddr(rocketMQnameServerAddress);
//         producer.setCreateTopicKey(TOPIC);
//
//         producer.start();
//         System.out.println("Producer started.");
//
//         return producer;
//     }
//
// }
