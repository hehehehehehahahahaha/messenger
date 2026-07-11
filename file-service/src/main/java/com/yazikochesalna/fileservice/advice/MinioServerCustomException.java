package com.yazikochesalna.fileservice.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MinioServerCustomException extends RuntimeException {

    public MinioServerCustomException(MessageType messageType, String sourceMessage) {
        super(messageType.getMessage() + " : " + sourceMessage);
    }

    @Getter
    @RequiredArgsConstructor
    public enum MessageType {
        INIT_BUCKET_FAILED("Failed to initialize MinIO bucket"),
        GET_FILE_STATS_ERROR("Error getting file stats"),
        GET_FILE_STATS_INTERNAL_ERROR("Internal error getting file stats"),
        INTERNAL_ERROR("MinIO internal error"),
        GET_STREAM_ERROR("Failed to get file stream"),
        DURING_UPLOAD_ERROR("Error during file upload")
        ;
        private final String message;


    }
}
