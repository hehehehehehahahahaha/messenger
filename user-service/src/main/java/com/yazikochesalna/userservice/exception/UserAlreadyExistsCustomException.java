package com.yazikochesalna.userservice.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsCustomException extends RuntimeException {
    public UserAlreadyExistsCustomException(MessageType messageType) {
        super(messageType.message);
    }

    @RequiredArgsConstructor
    public enum MessageType{
        MESSAGE_RUS("Такой username уже существует"),
        MESSAGE_ENG("Username already exists")
        ;
        private final String message;
    }
}
