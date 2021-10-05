package me.littlelenim.mythought.thought.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MaxTagLengthExceededException extends RuntimeException {
    public MaxTagLengthExceededException(String message) {
        super(message);
    }
}
