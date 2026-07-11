package com.yazikochesalna.fileservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MinioFileNotFoundCustomException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "File not found: ";

    public MinioFileNotFoundCustomException(String filePath) {
        super(MESSAGE_PREFIX + filePath);
    }
}


