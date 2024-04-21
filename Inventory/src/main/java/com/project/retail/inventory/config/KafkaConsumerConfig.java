package com.project.retail.inventory.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import java.util.HashMap;
import java.util.Map;


//@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    //使用map给kafka Consumer进行配置
    @Bean
    public ConsumerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress); //配置broker地址
        configProps.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class); //指定key的反序列化器
        configProps.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class); //指定value的反序列化器
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    //用于创建并配置 Kafka 消费者的监听容器工厂：用于创建 Kafka consumer的监听容器，以接收指定主题的消息，并使用配置好的消费者工厂进行消费者的创建和配置。
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory consumerFactory) { //consumerFactory会被自动注入
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>(); //监听容器工厂用于处理键和值都为字符串类型的消息
        factory.setConsumerFactory(consumerFactory); //将 Kafka 消费者工厂 设置到监听容器工厂中
        return factory;
    }


    //监听下单的topic
    @KafkaListener(topics = "placeOrder",groupId = "${spring.kafka.consumer.group-id.rewards}")
    public void onOrder(String message){
        System.out.println("message: "+message);
    }

}

