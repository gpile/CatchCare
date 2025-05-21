package it.catchcare.trapiot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Jackson ObjectMapper.
 * This class is responsible for creating and configuring the ObjectMapper bean used for JSON serialization and deserialization.
 * Needed because this service does not have the spring-boot-starter-web dependency, which would automatically configure it.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        // Register the JavaTimeModule to handle Java 8 date/time types
        om.registerModule(new JavaTimeModule());
        return om;
    }
}