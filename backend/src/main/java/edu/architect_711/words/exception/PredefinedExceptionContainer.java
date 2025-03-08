package edu.architect_711.words.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import edu.architect_711.words.entities.dto.ExceptionDescription;
import edu.architect_711.words.service.exception.ExceptionContainer;
import jakarta.persistence.EntityNotFoundException;

@Component
public class PredefinedExceptionContainer implements ExceptionContainer {
    private static final Map<Class<? extends Exception>, ExceptionDescription> exceptions = new HashMap<>();

    {
        save(EntityNotFoundException.class, HttpStatus.BAD_REQUEST, "e1");
        save(DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST, "e2");
    }

    @Override
    public ExceptionDescription findByClass(Class<? extends Exception> key) {
        return exceptions.get(key);
    }

    @Override
    public ExceptionDescription save(Class<? extends Exception> key, HttpStatus status, String code) {
        return exceptions.put(key, new ExceptionDescription(status, code));
    }

}