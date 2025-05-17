package edu.architect_711.words.service.exception;

import edu.architect_711.words.entities.dto.ExceptionDescription;
import edu.architect_711.words.entities.dto.ExceptionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Slf4j
public class ContainerBasedExceptionService implements ExceptionService {
    private final ExceptionContainer exceptionContainer;
    private final ExceptionResponseBuilder responseBuilder;

    @Override
    public ExceptionResponseDto handle(Exception exception) {
        log.error("Caught the exception: ", exception);

        if (!(exception instanceof RuntimeException runtimeException))
            return defaultResponse(exception);

        final ExceptionDescription foundException = exceptionContainer.findByClass(runtimeException.getClass());
        return foundException == null
                ? defaultResponse(new Exception(runtimeException))
                : runtimeResponse(foundException, runtimeException);
    }

    private ExceptionResponseDto defaultResponse(Exception exception) {
        return responseBuilder.buildDefault(exception);
    }

    private ExceptionResponseDto runtimeResponse(ExceptionDescription ed, RuntimeException re) {
        return responseBuilder.buildDto(ed, re.getMessage());
    }
}
