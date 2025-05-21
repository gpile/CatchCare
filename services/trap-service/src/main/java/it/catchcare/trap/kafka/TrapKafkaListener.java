package it.catchcare.trap.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.catchcare.common.domain.kafka.TrapArmedEvent;
import it.catchcare.common.domain.kafka.TrapClosedEvent;
import it.catchcare.common.domain.kafka.TrapEvent;
import it.catchcare.trap.model.Trap;
import it.catchcare.trap.service.TrapService;
import it.catchcare.trap.util.TrapStatus;
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
            TrapEvent event = objectMapper.readValue(message, TrapEvent.class);
//            log.debug("Deserialized Trap: {}", event);

            switch (event){
                case TrapClosedEvent trapClosedEvent -> {
                    log.info("Trap closed event: {}", trapClosedEvent);
                    // Handle the trap closed event
                    trapService.saveOrUpdate(new Trap(trapClosedEvent.trapId(), TrapStatus.CLOSED, null, trapClosedEvent.timestamp()));
                }
                case TrapArmedEvent trapArmedEvent -> {
                    log.info("Trap armed event: {}", trapArmedEvent);
                    // Handle the trap armed event
                    trapService.saveOrUpdate(new Trap(trapArmedEvent.trapId(), TrapStatus.ARMED, null, trapArmedEvent.timestamp()));
                }
                default -> log.warn("Unknown event type: {}", event.getClass());
            }

        } catch (Exception e) {
            log.error("Error deserializing message: {}", e.getMessage());
        }

    }

}
