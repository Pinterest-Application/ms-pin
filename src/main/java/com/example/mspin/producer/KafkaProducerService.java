package com.example.mspin.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T> CompletableFuture<SendResult<String, Object>> send(String topic, String key, T payload) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, payload);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to deliver Kafka message | topic: {}, key: {}, error: {}",
                        topic, key, ex.getMessage(), ex);
            } else {
                log.info("Kafka message delivered successfully | topic: {}, partition: {}, offset: {}, key: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset(),
                        key);
            }
        });

        return future;
    }

    public <T> CompletableFuture<SendResult<String, Object>> send(String topic, T payload) {
        return send(topic, null, payload);
    }
}