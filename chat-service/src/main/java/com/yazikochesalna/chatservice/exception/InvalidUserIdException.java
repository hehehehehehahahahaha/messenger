package com.yazikochesalna.chatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidUserIdException extends RuntimeException {

    private static final String MESSAGE = "Invalid user id";

    public InvalidUserIdException() {
        super(MESSAGE);
    }
}
