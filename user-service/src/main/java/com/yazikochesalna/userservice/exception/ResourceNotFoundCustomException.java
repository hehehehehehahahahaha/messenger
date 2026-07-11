package com.yazikochesalna.userservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundCustomException extends RuntimeException {

    public ResourceNotFoundCustomException(MessageType messageType) {
        super(messageType.message);
    }
    public ResourceNotFoundCustomException(MessageType messageType, Object... args) {
        super(messageType.getMessage().formatted(args));
    }
    @Getter
    @RequiredArgsConstructor
    public enum MessageType{
        USER_NOT_FOUND("User not found"),
        USER_NOT_FOUND_WITH_ID("User not found with id: %d"),
        USER_NOT_FOUND_WITH_ID_RUS("Пользователь с %d не найден")
        ;
        private final String message;
    }
}


