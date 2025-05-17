package edu.architect_711.words.controller;

import edu.architect_711.words.entities.dto.ExceptionResponseDto;
import edu.architect_711.words.service.exception.ContainerBasedExceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice @RequiredArgsConstructor
public class ExceptionController {
    private final ContainerBasedExceptionService exceptionService;

    @ExceptionHandler(Exception.class) 
    public ResponseEntity<ExceptionResponseDto> exception(Exception ex) {
        final ExceptionResponseDto response = exceptionService.handle(ex);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
}
