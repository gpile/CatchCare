package it.catchcare.common.domain.mqtt;

public record TrapStatusMqttMessage(String trapId, MqttMessageType type, boolean closed) implements TrapMqttMessage {
    public TrapStatusMqttMessage {
        if (trapId == null || trapId.isBlank()) {
            throw new IllegalArgumentException("trapId cannot be null or blank");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        if (MqttMessageType.STATUS != type) {
            throw new IllegalArgumentException("type must be STATUS");
        }
    }
}