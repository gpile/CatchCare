package it.catchcare.trapiot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TrapIoTServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrapIoTServiceApplication.class, args);
	}

}
