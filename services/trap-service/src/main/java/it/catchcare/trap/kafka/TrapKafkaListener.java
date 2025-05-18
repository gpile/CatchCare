package it.catchcare.trap.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.catchcare.common.domain.TrapClosedEvent;
import it.catchcare.trap.model.Trap;
import it.catchcare.trap.service.TrapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrapKafkaListener {

    private final TrapService trapService;
    private final ObjectMapper objectMapper;

    public TrapKafkaListener(TrapService trapService, ObjectMapper objectMapper) {
        this.trapService = trapService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "trap-events", groupId = "trap-service")
    public void handleTrapEvent(String message) {
        log.debug("Received message from Kafka on thread [{}]: {}", Thread.currentThread(), message);

        try {
            // Deserialize the message to Trap object
            TrapClosedEvent trap = objectMapper.readValue(message, TrapClosedEvent.class);
            log.debug("Deserialized Trap: {}", trap);

            // Save or update the trap in the database
            trapService.saveOrUpdate(new Trap(trap.trapId(), "CLOSED", null, trap.timestamp().toString()));
            log.debug("Trap saved/updated in the database: {}", trap);

        } catch (Exception e) {
            log.error("Error deserializing message: {}", e.getMessage());
        }

    }

}
