package edu.architect_711.words.service.exception;

import edu.architect_711.words.entities.dto.ExceptionDescription;
import edu.architect_711.words.entities.dto.ExceptionResponseDto;

public interface ExceptionResponseBuilder {
    ExceptionResponseDto buildDto(ExceptionDescription ed, String description);
    ExceptionResponseDto buildDefault(Exception exception);
}
