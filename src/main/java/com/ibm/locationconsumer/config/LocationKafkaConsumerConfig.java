package com.ibm.locationconsumer.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class LocationKafkaConsumerConfig {

    @Value("${spring.kafka.topic}")
    private String topic;

    @Value("${spring.kafka.groupId}")
    private String groupId;

    @Bean
    public ReactiveKafkaConsumerTemplate<Object, Object> reactiveKafkaConsumerTemplate(){
        return new ReactiveKafkaConsumerTemplate<>(createReceiver());
    }

    @Bean
    public KafkaReceiver<Object, Object> createReceiver() {
        Map<String, Object> consumerProps = new HashMap<>();

        // Required Confluent Cloud Configuration
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-4r087.us-west2.gcp.confluent.cloud:9092");
        consumerProps.put("schema.registry.url", "https://psrc-q8qx7.us-central1.gcp.confluent.cloud");
        consumerProps.put("basic.auth.credentials.source", "USER_INFO");
        consumerProps.put("schema.registry.basic.auth.user.info", "S5HL45TLP7JT6BXB:DLcIqumrpuXpUIOc1wdYvy6pvCdKHJ6n+RMRBArlUHityzLdPl5PtSrZo9J7JK7o");

        // General consumer Configuration
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        // SASL_SSL Security Configuration
        consumerProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "https");
        consumerProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        consumerProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        consumerProps.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"2Q33U66OQW2BNHP5\" password=\"WG0GZcP9TFv9SpmJbbkGDJvFQKWXGfgCXTiqjBFTJU0p8ifZCBQEguTOIez7DDsW\";");

        ReceiverOptions<Object, Object> receiverOptions = ReceiverOptions.create(consumerProps).subscription(Collections.singleton(topic));

        return KafkaReceiver.create(receiverOptions);
    }


    @Bean
    public ConsumerFactory<String, String> consumerFactory()
    {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-4r087.us-west2.gcp.confluent.cloud:9092");
        config.put("schema.registry.url", "https://psrc-q8qx7.us-central1.gcp.confluent.cloud");
        config.put("basic.auth.credentials.source", "USER_INFO");
        config.put("schema.registry.basic.auth.user.info", "S5HL45TLP7JT6BXB:DLcIqumrpuXpUIOc1wdYvy6pvCdKHJ6n+RMRBArlUHityzLdPl5PtSrZo9J7JK7o");

        config.put(ConsumerConfig.GROUP_ID_CONFIG, "ConsumerGroup1");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(JsonDeserializer.KEY_DEFAULT_TYPE, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class.getName());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        config.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        config.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"2Q33U66OQW2BNHP5\" password=\"WG0GZcP9TFv9SpmJbbkGDJvFQKWXGfgCXTiqjBFTJU0p8ifZCBQEguTOIez7DDsW\";");
        return new DefaultKafkaConsumerFactory<>(config);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setIdleBetweenPolls(10000);
        return factory;
    }

}
