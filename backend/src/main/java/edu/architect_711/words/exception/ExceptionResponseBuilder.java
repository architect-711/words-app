package edu.architect_711.words.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component @RequiredArgsConstructor
public class ExceptionResponseBuilder {
    private final Environment env;

    public ResponseEntity<GlobalErrorResponse> buildResponse(HttpStatus status, String code, String description) {
        return ResponseEntity.status(status).body(new GlobalErrorResponse(
                LocalDateTime.now(),
                env.getProperty(code, "An exception happened in the system."),
                description
        ));
    }
}
