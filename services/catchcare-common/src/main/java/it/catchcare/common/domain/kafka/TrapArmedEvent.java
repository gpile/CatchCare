package it.catchcare.common.domain.kafka;

import java.time.Instant;

public record TrapArmedEvent(String trapId, Instant timestamp) implements TrapEvent {
    // Record constructor to validate the parameters
    public TrapArmedEvent {
        if (trapId == null || trapId.isBlank()) {
            throw new IllegalArgumentException("trapId cannot be null or blank");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("timestamp cannot be null");
        }
    }
}
