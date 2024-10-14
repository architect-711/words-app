package edu.architect_711.words.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor @Data
public class BaseExceptionDescription {
    private String publicMessage;
    private String privateMessage;
    @NonNull
    private ExceptionType exceptionType = ExceptionType.UNKNOWN;

    public BaseExceptionDescription(@NonNull String publicMessage, @NonNull String privateMessage) {
        this.publicMessage = publicMessage;
        this.privateMessage = privateMessage;
    }
}
