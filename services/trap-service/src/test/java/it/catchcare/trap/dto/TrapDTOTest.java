package it.catchcare.trap.dto;

import it.catchcare.trap.util.TrapStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrapDTOTest {

    @Test
    void testConstructorsAndGettersSetters() {
        // Test empty constructor
        TrapDTO dto = new TrapDTO();
        assertNotNull(dto);

        // Test setters
        Instant now = Instant.now();
        dto.setId("123");
        dto.setName("Trap1");
        dto.setStatus(TrapStatus.ARMED.toString());
        dto.setBattery(95);
        dto.setLocation("45.4642,9.1900");
        dto.setLastUpdated(now.toString());

        // Test getters
        assertEquals("123", dto.getId());
        assertEquals("Trap1", dto.getName());
        assertEquals(TrapStatus.ARMED.toString(), dto.getStatus());
        assertEquals(95, dto.getBattery());
        assertEquals("45.4642,9.1900", dto.getLocation());
        assertEquals(now.toString(), dto.getLastUpdated());

        // Test all-args constructor
        TrapDTO dto2 = new TrapDTO("456", "Trap2", TrapStatus.CLOSED.toString(), 80, "40.7128,74.0060", now.toString());
        assertEquals("456", dto2.getId());
        assertEquals("Trap2", dto2.getName());
        assertEquals(TrapStatus.CLOSED.toString(), dto2.getStatus());
        assertEquals(80, dto2.getBattery());
        assertEquals("40.7128,74.0060", dto2.getLocation());
        assertEquals(now.toString(), dto2.getLastUpdated());
    }
}
