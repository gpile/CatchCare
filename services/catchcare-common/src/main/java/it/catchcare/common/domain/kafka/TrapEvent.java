package it.catchcare.common.domain.kafka;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// Enables polymorphic type handling for JSON serialization/deserialization.
// The "type" property in the JSON will indicate which subtype to use.
@JsonTypeInfo(
use = JsonTypeInfo.Id.NAME,                 // Use a logical type name (not the Java class) to identify the subtype
        include = JsonTypeInfo.As.PROPERTY, // Embed the type information as a JSON property
        property = "type"                   // The JSON property name that will carry the subtype identifier
)
// Declares the possible subtypes for this interface, mapping each type name to its class.
@JsonSubTypes({
        @JsonSubTypes.Type(value = TrapArmedEvent.class, name = "TrapArmedEvent"),
        @JsonSubTypes.Type(value = TrapClosedEvent.class, name = "TrapClosedEvent")
})
public sealed interface TrapEvent permits TrapArmedEvent, TrapClosedEvent {
}