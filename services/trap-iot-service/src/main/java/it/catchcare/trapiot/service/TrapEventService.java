package it.catchcare.trapiot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.catchcare.common.domain.kafka.TrapArmedEvent;
import it.catchcare.common.domain.kafka.TrapClosedEvent;
import it.catchcare.common.domain.mqtt.TrapMqttMessage;
import it.catchcare.common.domain.mqtt.TrapStatusMqttMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@Slf4j
public class TrapEventService {

    private final TrapKafkaProducer trapKafkaProducer;
    private final ObjectMapper objectMapper;

    public TrapEventService(TrapKafkaProducer trapKafkaProducer, ObjectMapper objectMapper) {
        this.trapKafkaProducer = trapKafkaProducer;
        this.objectMapper = objectMapper;
    }

    @Async
    /**
     * Processes the MQTT trap event payload asynchronously.
     * This method is annotated with @Async to allow for non-blocking execution.
     *
     * It uses virtual threads for better resource utilization, thanks to the Spring Executor provided by default
     * due to the @EnableAsync annotation in ServiceConfig and the virtual threads enabled in application.yaml
     *
     * For more details, refer to the official documentation:
     * <a href="https://docs.spring.io/spring-boot/reference/features/task-execution-and-scheduling.html">
     * Spring Task Execution and Scheduling
     * </a>
     *
     * @param payload The payload containing trap event data.
     */
    public void processEvent(String payload) {
        log.info("Processing payload on thread [" + Thread.currentThread() + "] with payload: " + payload);

        if (payload == null || payload.isBlank()) {
            log.warn("Received null or empty payload, skipping processing.");
            return;
        }

        try {
            // Deserialize the payload into a TrapMqttMessage object
            TrapMqttMessage message = objectMapper.readValue(payload, TrapMqttMessage.class);

            switch(message) { // pattern matching for switch

                case TrapStatusMqttMessage statusMessage -> {
                    // trap status changed
                    log.info("Trap #{} status changed: {}", statusMessage.trapId(), statusMessage.closed()? "closed" : "open");
                    if (statusMessage.closed())
                        trapKafkaProducer.sendEvent("trap-events", new TrapClosedEvent(statusMessage.trapId(), Instant.now()));
                    else
                        trapKafkaProducer.sendEvent("trap-events", new TrapArmedEvent(statusMessage.trapId(), Instant.now()));
                }

                default -> log.warn("Unknown message type: {}", message.getClass());
            }

        } catch (JsonProcessingException e) {
            log.error("Error deserializing payload: {}", e.getMessage());
        }

    }

}

