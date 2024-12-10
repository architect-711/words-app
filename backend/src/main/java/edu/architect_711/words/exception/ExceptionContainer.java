package edu.architect_711.words.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ExceptionContainer {
    private static final Map<Class<? extends RuntimeException>, ExceptionDescription> exceptions = new HashMap<>();

    static {
        put(EntityNotFoundException.class, HttpStatus.BAD_REQUEST, "e1");
        put(DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST, "e2");
    }

    private static void put(Class<? extends RuntimeException> key, HttpStatus status, String code) {
        exceptions.put(key, new ExceptionDescription(status, code));
    }

    public static ExceptionDescription get(Class<? extends RuntimeException> key) {
        return exceptions.get(key);
    }
}
