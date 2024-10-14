package edu.architect_711.words.exception;

import org.springframework.security.core.AuthenticationException;

public class DetailedAuthenticationException extends AuthenticationException {

    public DetailedAuthenticationException(String msg) {
        super(msg);
    }

    public DetailedAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
