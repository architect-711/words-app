package edu.architect_711.words.service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.architect_711.words.entities.dto.ExceptionDescription;
import edu.architect_711.words.entities.dto.ExceptionResponseDto;
import edu.architect_711.words.controller.service.ExceptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class ContainerBasedExceptionService implements ExceptionService {
    private final ExceptionContainer exceptionContainer;
    private final ExceptionResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<ExceptionResponseDto> exception(Exception exception) {
        log.error("Cought the exception: ", exception);

        if (!(exception instanceof RuntimeException runtimeException))
            return defaultResponse(exception);

        ExceptionDescription foundException = exceptionContainer.findByClass(runtimeException.getClass());
        return foundException == null
                ? defaultResponse(new Exception(runtimeException))
                : runtimeResponse(foundException, runtimeException);
    }

    private ResponseEntity<ExceptionResponseDto> defaultResponse(Exception exception) {
        return ResponseEntity.badRequest().body(responseBuilder.buildDefault(exception));
    }

    private ResponseEntity<ExceptionResponseDto> runtimeResponse(ExceptionDescription ed, RuntimeException re) {
        return ResponseEntity.status(ed.getStatus()).body(responseBuilder.buildDto(ed, re.getMessage()));
    }
}
