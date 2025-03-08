package edu.architect_711.words.controller.service;

import org.springframework.http.ResponseEntity;

import edu.architect_711.words.entities.dto.ExceptionResponseDto;

public interface ExceptionService {
    ResponseEntity<ExceptionResponseDto> exception(Exception exception);
}