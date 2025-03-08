package edu.architect_711.words.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import edu.architect_711.words.entities.dto.ExceptionDescription;
import edu.architect_711.words.entities.dto.ExceptionResponseDto;
import edu.architect_711.words.service.exception.ExceptionResponseBuilder;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DefaultExceptionResponseBuilder implements ExceptionResponseBuilder {
    private final static String DEFAULT_EX_CODE = "e1";
    private final static String DEFAULT_MESSAGE = "An exception happened in the system.";

    private final Environment env;

    @Override
    public ExceptionResponseDto buildDto(ExceptionDescription ed, String description) {
        return new ExceptionResponseDto(
                LocalDateTime.now(),
                env.getProperty(ed.getCode(), DEFAULT_MESSAGE),
                description);
    }

    @Override
    public ExceptionResponseDto buildDefault(Exception exception) {
        return new ExceptionResponseDto(
                LocalDateTime.now(),
                env.getProperty(DEFAULT_EX_CODE, DEFAULT_MESSAGE),
                exception.getMessage());
    }

}
