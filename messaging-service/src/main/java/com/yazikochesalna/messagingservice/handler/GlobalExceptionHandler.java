package com.yazikochesalna.messagingservice.handler;

import com.yazikochesalna.messagingservice.dto.exception.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String PATH = "path";
    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> handleInvalidMessageFormatCustomException(RuntimeException ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put(PATH, request.getDescription(false));
        details.put(TIMESTAMP, LocalDateTime.now());

        var errorResponse = new ExceptionDTO(ex.getMessage(), details);
        return new ResponseEntity<>(errorResponse, getStatus(ex.getClass()));
    }

    private HttpStatus getStatus(Class<? extends Throwable> exceptionClass) {
        ResponseStatus responseStatus = exceptionClass.getAnnotation(ResponseStatus.class);
        if (responseStatus != null) {
            return responseStatus.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
