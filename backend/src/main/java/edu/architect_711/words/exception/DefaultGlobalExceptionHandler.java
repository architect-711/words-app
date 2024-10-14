package edu.architect_711.words.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice @Slf4j
public class DefaultGlobalExceptionHandler {
    private final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseExceptionDescription> defaultHandler(final Exception exception) {
        log.error("DefaultExceptionHandler encountered exception: ", exception);

        final DefaultExceptionResponseDescription foundException = ExceptionDescriptionContainer.getExceptionDescriptionByClass(exception.getClass());

        if (foundException != null) 
            return ResponseEntity.status(foundException.getStatus()).body(foundException.getBaseExceptionDescription());
        return ResponseEntity.status(DEFAULT_HTTP_STATUS).body(new BaseExceptionDescription("Unexpected exception happened!", exception.getMessage()));
    }

}
