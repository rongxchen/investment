//package rongxchen.investment.config;
//
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class RocketMqConfig {
//
//    @Configuration
//    @ConfigurationProperties("rocketmq")
//    @Data
//    public static class RocketMqProperties {
//        private String nameSever;
//        @Value("${rocketmq.producer.group}")
//        private String producerGroup;
//    }
//
//    private final RocketMqProperties rocketMqProperties;
//
//    @Bean
//    public RocketMQTemplate rocketMQTemplate() {
//        DefaultMQProducer producer = new DefaultMQProducer();
//        producer.setNamesrvAddr(rocketMqProperties.getNameSever());
//        producer.setProducerGroup(rocketMqProperties.getProducerGroup());
//        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
//        rocketMQTemplate.setProducer(producer);
//        return rocketMQTemplate;
//    }
//
//}
