package com.yazikochesalna.userservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String PATH = "path";
    private static final String URI_PREFIX = "uri=";
    private static final String ERRORS = "errors";
    private static final String VALIDATION_FAILED_MESSAGE = "Validation failed";
    private static final String DEFAULT_MESSAGE = "Произошла ошибка";
    private final String VALIDATION_ERROR_MESSAGE = "Validation error";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put(TIMESTAMP, LocalDateTime.now());
        details.put(PATH, request.getDescription(false).replace(URI_PREFIX, ""));

        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : VALIDATION_ERROR_MESSAGE
                ));

        details.put(ERRORS, errors);

        return new ResponseEntity<>(
                new CustomErrorResponse(VALIDATION_FAILED_MESSAGE, details),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put(TIMESTAMP, LocalDateTime.now());
        details.put(PATH, request.getDescription(false).replace(URI_PREFIX, ""));

        String message = ex.getMessage() != null ? ex.getMessage() : DEFAULT_MESSAGE;

        return new ResponseEntity<>(
                new CustomErrorResponse(message, details),
                getStatus(ex)
        );
    }

    private HttpStatus getStatus(Exception ex) {
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        if (responseStatus != null) {
            return responseStatus.value();
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}