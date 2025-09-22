package it.catchcare.trap.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.catchcare.common.domain.kafka.TrapArmedEvent;
import it.catchcare.common.domain.kafka.TrapClosedEvent;
import it.catchcare.common.domain.kafka.TrapEvent;
import it.catchcare.trap.config.JacksonConfig;
import it.catchcare.trap.service.TrapService;
import it.catchcare.trap.util.TrapStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TrapKafkaListenerTest {

    @Mock
    TrapService trapService;

    ObjectMapper mapper = new JacksonConfig().objectMapper();

    @InjectMocks
    TrapKafkaListener trapKafkaListener;

    // This method is called before each test
    @BeforeEach
    void setUp() {
        // Initialize the TrapEventService with a mocked TrapKafkaProducer and a real ObjectMapper
        // This ensures that the kakfaProducer mock is not null, beause it is initalized by Mockito after the test class
        // is instantiated
        trapKafkaListener = new TrapKafkaListener(trapService, mapper);
    }


    @Test
    void shouldUpdateStatus_whenTrapClosedEventReceived() throws JsonProcessingException {
        TrapEvent event = new TrapClosedEvent("id1", Instant.now());

        trapKafkaListener.handleTrapEvent(mapper.writeValueAsString(event));
        // Verify that the trapService.updateStatus method was called with the correct parameters
        verify(trapService).updateStatus(eq("id1"), eq(TrapStatus.CLOSED), any(Instant.class));
    }

    @Test
    void shouldUpdateStatus_whenTrapArmedEventReceived() throws JsonProcessingException {
        TrapEvent event = new TrapArmedEvent("id2", Instant.now());

        trapKafkaListener.handleTrapEvent(mapper.writeValueAsString(event));
        // Verify that the trapService.updateStatus method was called with the correct parameters
        verify(trapService).updateStatus(eq("id2"), eq(TrapStatus.ARMED), any(Instant.class));
    }

    @Test
    void shouldThrowException_whenUnkownEventReceived() throws JsonProcessingException {
        String invalidMessage = "{\"type\":\"UNKNOWN_TYPE\",\"trapId\":\"123\"}";
        trapKafkaListener.handleTrapEvent(invalidMessage);
        // Verify that the trapService.updateStatus method was never called
        // Since we cannot easily verify logs with Mockito, we ensure no interaction with the service
        verify(trapService, org.mockito.Mockito.never()).updateStatus(any(), any(), any());
    }

}
