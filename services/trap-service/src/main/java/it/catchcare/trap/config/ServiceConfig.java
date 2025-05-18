package it.catchcare.trap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
// Annotation needed to enable asynchronous method execution in Spring (@Async), used in combination with virtual
// threads enabled in application.yaml, which allows for non-blocking I/O operations and better resource utilization.
@EnableAsync
public class ServiceConfig {
}
