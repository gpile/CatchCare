package it.catchcare.trap.model;

import it.catchcare.trap.util.TrapStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrapTest {

    @Test
    void testConstructorsAndGettersSettersAntToString() {
        // Test empty constructor
        Trap trap = new Trap();
        assertNotNull(trap);

        // Test setters
        Instant now = Instant.now();
        trap.setId("123");
        trap.setName("Trap1");
        trap.setStatus(TrapStatus.ARMED);
        trap.setBattery(95);
        trap.setLocation("45.4642,9.1900");
        trap.setLastUpdated(now);

        // Test getters
        assertEquals("123", trap.getId());
        assertEquals("Trap1", trap.getName());
        assertEquals(TrapStatus.ARMED, trap.getStatus());
        assertEquals(95, trap.getBattery());
        assertEquals("45.4642,9.1900", trap.getLocation());
        assertEquals(now, trap.getLastUpdated());

        // Test all-args constructor and builder
        Trap builderTrap = Trap.builder()
                .id("456")
                .name("Trap2")
                .status(TrapStatus.CLOSED)
                .battery(85)
                .location("45.4642,9.1900")
                .lastUpdated(now)
                .build();

        assertEquals("456", builderTrap.getId());
        assertEquals("Trap2", builderTrap.getName());
        assertEquals(TrapStatus.CLOSED, builderTrap.getStatus());
        assertEquals(85, builderTrap.getBattery());
        assertEquals("45.4642,9.1900", builderTrap.getLocation());
        assertEquals(now, builderTrap.getLastUpdated());

        // Test toString
        String toStringOutput = builderTrap.toString();
        assertNotNull(toStringOutput);
        assert(toStringOutput.contains("Trap2"));
    }
}
