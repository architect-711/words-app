package edu.architect_711.words.service.exception;

import edu.architect_711.words.entities.dto.ExceptionResponseDto;

public interface ExceptionService {
    ExceptionResponseDto handle(Exception exception);
}
