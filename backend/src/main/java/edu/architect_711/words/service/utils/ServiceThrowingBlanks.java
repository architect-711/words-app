package edu.architect_711.words.service.utils;

import jakarta.persistence.EntityNotFoundException;

import java.util.function.Supplier;

public interface ServiceThrowingBlanks {
    default Supplier<RuntimeException> personNotFoundById(Long id) {
        return () -> new EntityNotFoundException("Couldn't find person with id: " + id);
    }

    default Supplier<RuntimeException> wordLanguageNotFoundByTitle(String title) {
        return () -> new EntityNotFoundException("Couldn't find word language with title: " + title);
    }

    default Supplier<RuntimeException> wordNotFoundById(Long id) {
        return () -> new EntityNotFoundException("Couldn't find word by id: " + id);
    }

}
