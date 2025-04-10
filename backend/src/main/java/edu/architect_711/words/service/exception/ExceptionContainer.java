package edu.architect_711.words.service.exception;

import org.springframework.http.HttpStatus;

import edu.architect_711.words.entities.dto.ExceptionDescription;

public interface ExceptionContainer {
    ExceptionDescription findByClass(Class<? extends Exception> key);
    ExceptionDescription save(Class<? extends Exception> key, HttpStatus status, String code);
}
