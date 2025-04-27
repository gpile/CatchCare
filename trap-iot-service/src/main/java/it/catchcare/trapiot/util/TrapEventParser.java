package it.catchcare.trapiot.util;

import it.catchcare.trapiot.domain.*;

import java.time.Instant;

public class TrapEventParser {

    public static TrapEvent parse(String type, String trapId, Instant timestamp) {
        return switch (type) {
            case "TRAP_ARMED" -> new TrapArmedEvent(trapId, timestamp);
            case "TRAP_CLOSED" -> new TrapClosedEvent(trapId, timestamp);
            default -> throw new IllegalArgumentException("Unknown event type: " + type);
        };
    }
}