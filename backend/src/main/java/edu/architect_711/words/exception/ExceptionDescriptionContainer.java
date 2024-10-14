package edu.architect_711.words.exception;

import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.springframework.http.HttpStatus.*;
import static edu.architect_711.words.exception.ExceptionType.*;

public class ExceptionDescriptionContainer {
    private final static HashMap<Class<? extends Exception>, DefaultExceptionResponseDescription> exceptionsRoadmap = new HashMap<>();

    static {
        exceptionsRoadmap.put(NoResourceFoundException.class, new DefaultExceptionResponseDescription(BAD_REQUEST, NAVIGATION, "This URI is not supported.", "Illegal URI."));
    } 

    public static DefaultExceptionResponseDescription getExceptionDescriptionByClass(final Class<? extends Exception> targetClass) {
        return exceptionsRoadmap.getOrDefault(targetClass, null);
    }
}
