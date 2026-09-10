package org.example.restservice.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.example.common.Constants;import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@Configuration
public class KafkaTopicConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(Constants.CREATE_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic updateTopic() {
        return new NewTopic(Constants.UPDATE_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic deleteTopic() {
        return new NewTopic(Constants.DELETE_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic readTopic() {
        return new NewTopic(Constants.READ_TOPIC, 1, (short) 1);
    }

    @Bean NewTopic readRepliesTopic() {
        return new NewTopic(Constants.READ_REPLIES_TOPIC, 1, (short) 1);
    }
}
