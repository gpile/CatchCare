package it.catchcare.trap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrapNotFoundException extends RuntimeException {
    public TrapNotFoundException() {
        super();
    }
}
