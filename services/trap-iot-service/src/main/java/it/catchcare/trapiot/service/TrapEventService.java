package it.catchcare.trapiot.service;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Log
public class TrapEventService {

    private final TrapKafkaProducer trapKafkaProducer;

    public TrapEventService(TrapKafkaProducer trapKafkaProducer) {
        this.trapKafkaProducer = trapKafkaProducer;
    }

    @Async
    /**
     * Processes the MQTT trap event payload asynchronously.
     * This method is annotated with @Async to allow for non-blocking execution.
     *
     * It uses virtual threads for better resource utilization, thanks to the Spring Executor provided by default
     * due to the @EnableAsync annotation in ServiceConfig and the virtual threads enabled in application.yaml
     *
     * For more details, refer to the official documentation:
     * <a href="https://docs.spring.io/spring-boot/reference/features/task-execution-and-scheduling.html">
     * Spring Task Execution and Scheduling
     * </a>
     *
     * @param payload The payload containing trap event data.
     */
    public void processEvent(String payload) {
        log.info("Processing payload on thread [" + Thread.currentThread() + "] with payload: " + payload);
        trapKafkaProducer.sendEvent("trap-events", payload);
    }

}

