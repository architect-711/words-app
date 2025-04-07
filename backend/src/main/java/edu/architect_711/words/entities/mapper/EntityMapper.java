package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.exception.custom.MethodNotImplementedException;

public interface EntityMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    default void alarm() {
        throw new MethodNotImplementedException("This method is replaced with another. The `@Deprecated` annotation is specified for who???");
    }
}