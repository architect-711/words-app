package edu.architect_711.words.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter @Setter @ToString
public class DefaultExceptionResponseDescription extends BaseExceptionDescription {
    private HttpStatus status;

    public DefaultExceptionResponseDescription(
            @NonNull HttpStatus status,
            ExceptionType exceptionType,
            String publicMessage,
            String privateMessage
    ) {
        super(publicMessage, privateMessage, exceptionType);
        this.status = status;
    }

    public BaseExceptionDescription getBaseExceptionDescription() {
        return new BaseExceptionDescription(getPublicMessage(), getPrivateMessage(), getExceptionType());
    }

}
