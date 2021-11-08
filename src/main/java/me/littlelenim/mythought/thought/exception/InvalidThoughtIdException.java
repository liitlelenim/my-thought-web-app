package me.littlelenim.mythought.thought.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // super! dobrze mieć wiele mówiące wyjątki
public class InvalidThoughtIdException extends RuntimeException {
    public InvalidThoughtIdException(String message){
        super(message);
    }
}
