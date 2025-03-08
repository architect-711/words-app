package edu.architect_711.words.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.architect_711.words.controller.service.ExceptionService;
import edu.architect_711.words.entities.dto.ExceptionResponseDto;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice @RequiredArgsConstructor
public class ExceptionController {
    private final ExceptionService exceptionService;

    @ExceptionHandler(Exception.class) 
    public ResponseEntity<ExceptionResponseDto> exception(Exception ex) {
        return exceptionService.exception(ex);
    }
}
