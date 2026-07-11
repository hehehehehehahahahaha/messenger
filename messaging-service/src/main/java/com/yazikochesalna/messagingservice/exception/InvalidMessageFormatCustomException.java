package com.yazikochesalna.messagingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMessageFormatCustomException extends RuntimeException {

    private static final String MESSAGE = "Invalid JSON format";

    public InvalidMessageFormatCustomException() {
        super(MESSAGE);
    }
}
