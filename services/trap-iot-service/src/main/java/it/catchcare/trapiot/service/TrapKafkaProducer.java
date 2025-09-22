package it.catchcare.trapiot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.catchcare.common.domain.kafka.TrapEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrapKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public TrapKafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendEvent(String topic, String payload) {
        kafkaTemplate.send(topic, payload);
    }

    public void sendEvent(String topic, TrapEvent event) throws JsonProcessingException {
        kafkaTemplate.send(topic, objectMapper.writeValueAsString(event));
    }
}
