package it.catchcare.common.domain.kafka;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TrapArmedEvent.class, name = "TrapArmedEvent"),
        @JsonSubTypes.Type(value = TrapClosedEvent.class, name = "TrapClosedEvent")
})
public sealed interface TrapEvent permits TrapArmedEvent, TrapClosedEvent {
}