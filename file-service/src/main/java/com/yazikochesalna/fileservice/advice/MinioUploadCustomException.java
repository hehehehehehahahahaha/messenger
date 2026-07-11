package com.yazikochesalna.fileservice.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MinioUploadCustomException extends RuntimeException {
    public MinioUploadCustomException(MessageType messageType, String sourceMessage) {
        super(messageType.message + " : " + sourceMessage);
    }
    @RequiredArgsConstructor
    public enum MessageType {
        INVALID_INPUT("Invalid input parameters"),
        PROCESSING_ERROR("File processing error"),
        UPLOAD_ERROR("Failed to upload file")
        ;
        private final String message;


    }
}
