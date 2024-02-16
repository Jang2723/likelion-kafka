package com.example.kafka;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    // Producer 설정
    public ProducerFactory<String, String> stringProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        //연결할 Kafka Broker들
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 데이터 직렬화
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Ack
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        // 데이터를 어떤 방식으로 특정 Partition에 전달할지
        configProps.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String ,String > stringKafkaTemplate() {
        return new KafkaTemplate<>(stringProducerFactory());
    }

    // Payload 보내기
    @Bean
    public ProducerFactory<String, PayloadDto> payloadProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        // 연결할 Kafka Broker들
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 데이터 직렬화
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, PayloadDto> payloadKafkaTemplate() {
        return new KafkaTemplate<>(payloadProducerFactory());
    }

}
