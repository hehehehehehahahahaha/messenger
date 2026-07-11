package com.yazikochesalna.fileservice.advice;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAttachedException extends RuntimeException {
    public NotAttachedException(MessageType messageType) {
        super(messageType.getMessage());
    }

    @Getter
    @RequiredArgsConstructor
    public enum MessageType {
        EMPTY_FILE("File is empty or not attached"),
        EMPTY_UUID_IN_METADATA("fileUUID not provided in metadata"),
        EMPTY_METADATA("Metadata cannot be null"),
        EMPTY_FILE_OWNER("Neither chatID nor userID provided in metadata")
        ;
        private final String message;
    }
}
