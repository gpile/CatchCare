package it.catchcare.trapiot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.catchcare.common.domain.kafka.TrapArmedEvent;
import it.catchcare.common.domain.kafka.TrapClosedEvent;
import it.catchcare.common.domain.mqtt.TrapStatusMqttMessage;
import it.catchcare.trapiot.config.JacksonConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TrapEventServiceTest {

    @Mock
    TrapKafkaProducer trapKafkaProducer;

    // Create a real ObjectMapper instance using the JacksonConfig class, which contains JavaTimeModule registration
    ObjectMapper mapper = new JacksonConfig().objectMapper();

    TrapEventService trapEventService;

    // This method is called before each test
    @BeforeEach
    void setUp() {
        // Initialize the TrapEventService with a mocked TrapKafkaProducer and a real ObjectMapper
        // This ensures that the kakfaProducer mock is not null, beause it is initalized by Mockito after the test class
        // is instantiated
        trapEventService = new TrapEventService(trapKafkaProducer, mapper);
    }

    @Test
    void shouldSendClosedEvent_whenTrapIsClosed() throws JsonProcessingException {
        TrapStatusMqttMessage msg = new TrapStatusMqttMessage("1", true);
        trapEventService.processEvent(mapper.writeValueAsString(msg));
        // Verify that event is sent to the trap-events topic and is of type TrapClosedEvent
        verify(trapKafkaProducer).sendEvent(eq("trap-events"), any(TrapClosedEvent.class));
    }

    @Test
    void shouldSendArmedEvent_whenTrapIsArmed() throws JsonProcessingException {
        TrapStatusMqttMessage msg = new TrapStatusMqttMessage("1", false);
        trapEventService.processEvent(mapper.writeValueAsString(msg));
        // Verify that event is sent to the trap-events topic and is of type TrapArmedEvent
        verify(trapKafkaProducer).sendEvent(eq("trap-events"), any(TrapArmedEvent.class));
    }

    @Test
    void shouldNotThrowException_whenProcessingInvalidJson() {
        String invalidJson = "{invalid";
        // Ensure that processing invalid JSON does not throw an exception
        assertDoesNotThrow(() -> trapEventService.processEvent(invalidJson));
    }

    @Test
    void shouldNotThrowExceptionOrSendEvent_whenProcessingEmptyPayload() {
        // Ensure that processing an empty payload does not throw an exception
        assertDoesNotThrow(() -> trapEventService.processEvent(""));
        // Verify that no event is sent when the payload is empty
        verify(trapKafkaProducer, never()).sendEvent(anyString(), anyString());
    }

    @Test
    void shouldNotThrowExceptionOrSendEvent_whenProcessingNullPayload() {
        String nullPayload = null;
        // Ensure that processing a null payload does not throw an exception
        assertDoesNotThrow(() -> trapEventService.processEvent(null));
        // Verify that no event is sent when the payload is null
        verify(trapKafkaProducer, never()).sendEvent(anyString(), anyString());
    }

}
