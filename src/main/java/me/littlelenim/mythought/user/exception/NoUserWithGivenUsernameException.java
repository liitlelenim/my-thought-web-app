package me.littlelenim.mythought.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserWithGivenUsernameException extends RuntimeException {
    public NoUserWithGivenUsernameException(String message) {
        super(message);
    }
}
