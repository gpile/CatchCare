package it.catchcare.trapiot.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrapKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TrapKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String topic, String payload) {
        kafkaTemplate.send(topic, payload);
    }
}
