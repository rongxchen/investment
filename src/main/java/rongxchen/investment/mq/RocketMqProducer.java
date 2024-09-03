//package rongxchen.investment.mq;
//
//import lombok.RequiredArgsConstructor;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class RocketMqProducer {
//
//    private final RocketMQTemplate rocketMQTemplate;
//
//    public void sendMessage(String topic, String message) {
//        rocketMQTemplate.convertAndSend(topic, message);
//    }
//
//}
