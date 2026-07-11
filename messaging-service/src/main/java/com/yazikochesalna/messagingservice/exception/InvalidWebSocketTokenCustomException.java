package com.yazikochesalna.messagingservice.exception;

public class InvalidWebSocketTokenCustomException extends RuntimeException {

    private static final String MESSAGE = "an invalid token has been specified or its TTL has expired";

    public InvalidWebSocketTokenCustomException() {
        super(MESSAGE);
    }
}
