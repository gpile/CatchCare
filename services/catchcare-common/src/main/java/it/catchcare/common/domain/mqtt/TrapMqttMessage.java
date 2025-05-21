package it.catchcare.common.domain.mqtt;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TrapStatusMqttMessage.class, name = "STATUS")
})
public sealed interface TrapMqttMessage permits TrapStatusMqttMessage {}