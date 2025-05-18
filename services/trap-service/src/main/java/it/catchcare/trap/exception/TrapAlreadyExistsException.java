package it.catchcare.trap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TrapAlreadyExistsException extends RuntimeException {
    public TrapAlreadyExistsException(String message) {
        super(message);
    }
}
