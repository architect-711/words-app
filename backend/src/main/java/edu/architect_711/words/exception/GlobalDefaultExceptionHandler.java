package edu.architect_711.words.exception;

import edu.architect_711.words.aop.ExceptionLoggable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice @RequiredArgsConstructor
public class GlobalDefaultExceptionHandler {
    private final ExceptionResponseBuilder exceptionResponseBuilder;

    @ExceptionHandler(RuntimeException.class)
    @ExceptionLoggable
    public ResponseEntity<GlobalErrorResponse> runtimeExceptionHandler(RuntimeException exception) {
        ExceptionDescription exceptionDescription = ExceptionContainer.get(exception.getClass());

        if (exceptionDescription != null)
            return exceptionResponseBuilder.buildResponse(exceptionDescription.getStatus(), exceptionDescription.getCode(), exception.getMessage());

        return defaultExceptionHandler(new Exception(exception));
    }

    @ExceptionHandler(Exception.class)
    @ExceptionLoggable
    public ResponseEntity<GlobalErrorResponse> defaultExceptionHandler(Exception exception) {
        return exceptionResponseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "e0", exception.getMessage());
    }

}
